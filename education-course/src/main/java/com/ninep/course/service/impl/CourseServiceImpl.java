package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.api.user.FeignUserService;
import com.ninep.common.DTO.CourseEsDTO;
import com.ninep.common.DTO.LecturerViewDTO;
import com.ninep.common.enums.*;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.*;
import com.ninep.course.admin.DTO.CourseEditDTO;
import com.ninep.course.admin.DTO.CourseSaveDTO;
import com.ninep.course.admin.response.CoursePageResp;
import com.ninep.course.admin.DTO.CoursePageDTO;
import com.ninep.course.api.response.ApiCategoryResp;
import com.ninep.course.api.response.ApiCoursePageResp;
import com.ninep.course.api.vo.ApiCoursePageVo;
import com.ninep.course.api.vo.AuthCourseSignVo;
import com.ninep.course.auth.response.AuthCourseSignResp;
import com.ninep.course.common.response.*;
import com.ninep.course.common.vo.CourseVo;
import com.ninep.course.entity.*;
import com.ninep.course.mapper.*;
import com.ninep.course.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private FeignUserService feignUserService;
    @Resource
    private CourseChapterMapper courseChapterMapper;
    @Resource
    private CourseChapterPeriodMapper courseChapterPeriodMapper;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private UserCourseMapper userCourseMapper;
    @Resource
    private UserStudyMapper userStudyMapper;
    @Resource
    private ZoneCourseMapper zoneCourseMapper;

    @Override
    public Page<CoursePageResp> pageList(CoursePageDTO pageVo) {
        IPage<Course> coursePage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Course::getCourseSort).orderByDesc(Course::getGmtModified);
        if (StringUtils.hasText(pageVo.getCourseName())){
            wrapper.like(Course::getCourseName,pageVo.getCourseName());
        }
        this.page(coursePage,wrapper);
        Page<Course> parse = PageUtil.parse(coursePage);
        Page<CoursePageResp> transform = PageUtil.transform(parse, CoursePageResp.class);
        List<CoursePageResp> coursePageResps = transform.getList();
        if (!CollectionUtils.isEmpty(coursePageResps)){
            //获取分类
            List<Category> categories = categoryMapper.selectList(null);
            if (!CollectionUtils.isEmpty(categories)){
                Map<Long, String> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName));
                for (CoursePageResp coursePageResp:coursePageResps){
                    coursePageResp.setCategoryName(categoryMap.get(coursePageResp.getCategoryId()));
                }
            }
            //获取讲师信息
            Map<Long, String> map = feignUserService.list();
            if (!MapUtil.isEmpty(map)){
                for (CoursePageResp coursePageResp:coursePageResps){
                    coursePageResp.setLecturerName(map.get(coursePageResp.getLecturerId()));
                }
            }
        }
        return transform;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id) {
        //删除关联表
        courseChapterMapper.delete(new LambdaQueryWrapper<CourseChapter>().eq(CourseChapter::getCourseId,id));
        courseChapterPeriodMapper.delete(new LambdaQueryWrapper<CourseChapterPeriod>().eq(CourseChapterPeriod::getCourseId,id));
        zoneCourseMapper.delete(new LambdaQueryWrapper<ZoneCourse>().eq(ZoneCourse::getCourseId,id));
        //删除用户课程表
        userCourseMapper.delete(new LambdaQueryWrapper<UserCourse>().eq(UserCourse::getCourseId,id));
        //删除用户学习记录
        userStudyMapper.delete(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getCourseId,id));
        this.removeById(id);
        //TODO  canal删除es对应数据
        return ResultEnum.OTHER_SUCCESS.getMsg();
    }

    @Override
    public String saveCourse(CourseSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, SysConfigEnum.CONFIG_ERROR);
        //计算价格
        if (saveVo.getCoursePrice()!=null){
            if (saveVo.getCoursePrice().compareTo(BigDecimal.ZERO)==0){
                saveVo.setIsFree(FreeEnum.FREE.getCode());
            } else if (saveVo.getCoursePrice().compareTo(BigDecimal.ZERO)==1) {
                saveVo.setIsFree(FreeEnum.CHARGE.getCode());
            } else if (saveVo.getCoursePrice().compareTo(BigDecimal.ZERO)==-1) {
                throw new BaseException(SysConfigEnum.CONFIG_ERROR.getCode(),SysConfigEnum.CONFIG_ERROR.getMsg());
            }
        }

        Course course = BeanUtil.copyProperties(saveVo, Course.class);
        //TODO canal操作es
        if (this.save(course)){
            if (PutawayEnum.UP.getCode().equals(course.getIsPutaway())){
                //EsCourse有的数据都会添加到es中，会进行自动类型的转换
                CourseEsDTO courseEsDTO= BeanUtil.copyProperties(course, CourseEsDTO.class);
                String index = elasticsearchRestTemplate.index(new IndexQueryBuilder().withObject(courseEsDTO).build(), IndexCoordinates.of(CourseEsDTO.COURSE));
                log.info("_docId:{}",index);
            }
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        throw new BaseException("添加失败");
    }

    @Override
    public String edit(CourseEditDTO editVo) {
        AssertUtil.notNull(editVo,SysConfigEnum.CONFIG_ERROR);
        AssertUtil.notNull(editVo.getId(),SysConfigEnum.CONFIG_ERROR);
        if (editVo.getCoursePrice()!=null){
            if (editVo.getCoursePrice().compareTo(BigDecimal.ZERO)==0){
                editVo.setIsFree(FreeEnum.FREE.getCode());
            } else if (editVo.getCoursePrice().compareTo(BigDecimal.ZERO)==1) {
                editVo.setIsFree(FreeEnum.CHARGE.getCode());
            } else if (editVo.getCoursePrice().compareTo(BigDecimal.ZERO)==-1) {
                throw new BaseException(SysConfigEnum.CONFIG_ERROR.getCode(),SysConfigEnum.CONFIG_ERROR.getMsg());
            }
        }
        Course course = BeanUtil.copyProperties(editVo, Course.class);
        if (this.updateById(course)){
            //更新es
            if (PutawayEnum.UP.getCode().equals(editVo.getIsPutaway())){
                CourseEsDTO courseEsDTO = BeanUtil.copyProperties(course, CourseEsDTO.class);
                elasticsearchRestTemplate.index(new IndexQueryBuilder().withObject(courseEsDTO).build(), IndexCoordinates.of(CourseEsDTO.COURSE));
            }
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    /**
     * 前端条件查询课程
     * @param pageVo
     * @return
     */
    @Override
    public Page<ApiCoursePageResp> searchForPage(ApiCoursePageVo pageVo) {
        NativeSearchQueryBuilder nsb = new NativeSearchQueryBuilder();
        // 高亮字段
        nsb.withHighlightFields(new HighlightBuilder.Field("courseName").preTags("<mark>").postTags("</mark>"));

        // 分页
        nsb.withPageable(PageRequest.of(pageVo.getPageCurrent() - 1, pageVo.getPageSize()));

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(pageVo.getCategoryId())) {
            //获取子类
            List<Long> categoryIdList = listCategoryId(pageVo.getCategoryId());
            qb.must(QueryBuilders.termsQuery("categoryId", categoryIdList));
        }
        if (ObjectUtil.isNotEmpty(pageVo.getIsFree())) {
            qb.must(QueryBuilders.termQuery("isFree", pageVo.getIsFree()));
        }
        if (StringUtils.hasText(pageVo.getCourseName())) {
            // 模糊查询multiMatchQuery，最佳字段best_fields
            qb.must(QueryBuilders.multiMatchQuery(pageVo.getCourseName(), "courseName").type(MultiMatchQueryBuilder.Type.BEST_FIELDS));
        } else {
            // 课程排序（courseSort）
            nsb.withSort(new FieldSortBuilder("courseSort").order(SortOrder.ASC));
            nsb.withSort(new FieldSortBuilder("id").order(SortOrder.DESC));
        }
        nsb.withQuery(qb);

        SearchHits<CourseEsDTO> searchHits = elasticsearchRestTemplate.search(nsb.build(), CourseEsDTO.class, IndexCoordinates.of(CourseEsDTO.COURSE));
        return EsPageUtil.transform(searchHits, pageVo.getPageCurrent(), pageVo.getPageSize(), ApiCoursePageResp.class);
    }

    @Override
    public CourseResp view(CourseVo courseVo, Long userId) {
        Course course = this.getById(courseVo.getCourseId());
        AssertUtil.notNull(course,"该课程不存在");
        AssertUtil.isTrue(StatusIdEnum.YES.getCode().equals(course.getStatusId()),"该课程已禁用");
        CourseResp courseResp = BeanUtil.copyProperties(course, CourseResp.class);
        //课时进度
        Map<Long, BigDecimal> userStudyProgressMap = new HashMap<>();
        //是否登入
        if (userId!=null){
            //是否是免费课程
            if(FreeEnum.FREE.getCode().equals(courseResp.getIsFree()) || BigDecimal.ZERO.compareTo(courseResp.getCoursePrice())==0){
                courseResp.setAllowStudy(1);
            }else {
                //收费课程，看是否已经购买
                UserCourse userCourse = userCourseMapper.selectOne(new LambdaQueryWrapper<UserCourse>().eq(UserCourse::getCourseId, courseResp.getId()));
                if (userCourse!=null){
                    courseResp.setAllowStudy(1);
                }
            }
            //课时进度
            List<UserStudy> userStudies = userStudyMapper.selectList(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getUserId, userId).in(UserStudy::getCourseId, courseResp.getId()));
            if (!CollUtil.isEmpty(userStudies)){
                userStudyProgressMap=userStudies.stream().collect(Collectors.toMap(UserStudy::getPeriodId,UserStudy::getProgress));
            }
        }

        //获取讲师信息
        LecturerViewDTO lecturerViewDTO = feignUserService.detail(courseResp.getLecturerId());
        courseResp.setLecturerResp(BeanUtil.copyProperties(lecturerViewDTO, CourseLecturerResp.class));

        //获取章节
        List<CourseChapterResp> chapterRespList =getCourseChapterResp(courseResp.getId(),userStudyProgressMap);
        courseResp.setChapterRespList(chapterRespList);
        return courseResp;
    }

    @Override
    public AuthCourseSignResp sign(AuthCourseSignVo signVo) {
        //TODO 视频播放

        //自动接上一回播放
        if (signVo.getCourseId()!=null){
            //获取最新学习的课时
            UserStudy userStudy = userStudyMapper.selectList(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getUserId, ThreadContextUtil.userId()).eq(UserStudy::getCourseId, signVo.getCourseId()).orderByDesc(UserStudy::getGmtModified)).get(0);
            if (userStudy!=null){
                signVo.setPeriodId(userStudy.getPeriodId());
            }else {
                throw new BaseException("请选择要学习的课时");
            }
        }
        //获取课时
        CourseChapterPeriod courseChapterPeriod = courseChapterPeriodMapper.selectById(signVo.getPeriodId());
        if (courseChapterPeriod==null || StatusIdEnum.NO.getCode().equals(courseChapterPeriod.getStatusId())){
            throw new BaseException("课时不存在或不可用");
        }
        if (courseChapterPeriod.getResourceId()==null){
            throw new BaseException("改课时没有设置资源");
        }
        //获取资源
        com.ninep.course.entity.Resource resource = resourceMapper.selectById(courseChapterPeriod.getResourceId());
        if (ObjectUtil.isEmpty(resource) || ResourceTypeEnum.DOC.getCode().equals(resource.getResourceType())) {
            throw new BaseException("该资源不存在或类型不正确");
        }
        if (!VideoStatusEnum.SUCCES.getCode().equals(resource.getVideoStatus())) {
            throw new BaseException("资源暂不可用");
        }
        if (!check(courseChapterPeriod)) {
            throw new BaseException("没购买，不允许播放");
        }
        //可以播放
        //获取进度
        UserStudy userStudy = userStudyMapper.selectOne(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getUserId,ThreadContextUtil.userId()).eq(UserStudy::getPeriodId,signVo.getPeriodId()));
        if (ObjectUtil.isEmpty(userStudy)) {
            userStudy = new UserStudy();
            userStudy.setCourseId(courseChapterPeriod.getCourseId());
            userStudy.setChapterId(courseChapterPeriod.getChapterId());
            userStudy.setPeriodId(courseChapterPeriod.getId());
            userStudy.setUserId(ThreadContextUtil.userId());
            userStudy.setProgress(BigDecimal.ZERO);
            userStudyMapper.insert(userStudy);
        }
        AuthCourseSignResp resp = new AuthCourseSignResp();
        resp.setStartTime(userStudy.getProgress().multiply(new BigDecimal(resource.getVideoLength())).divide(BigDecimal.valueOf(100)).longValue());
        resp.setStudyId(userStudy.getId());
        resp.setPeriodId(signVo.getPeriodId());
        resp.setResourceId(resource.getId());
        resp.setVid(resource.getVideoVid());
        // 播放参数
        //TODO
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByLecturerId(Long lecturedId) {
        //获取对应课程id
        List<Course> courseList = this.list(new LambdaQueryWrapper<Course>().eq(Course::getLecturerId, lecturedId));
        if (CollUtil.isNotEmpty(courseList)){
            List<Long> courseIds = courseList.stream().map(Course::getId).collect(Collectors.toList());
            //删除相关表
            courseChapterMapper.delete(new LambdaQueryWrapper<CourseChapter>().in(CourseChapter::getCourseId,courseIds));
            courseChapterPeriodMapper.delete(new LambdaQueryWrapper<CourseChapterPeriod>().in(CourseChapterPeriod::getCourseId,courseIds));
            userStudyMapper.delete(new LambdaQueryWrapper<UserStudy>().in(UserStudy::getCourseId,courseIds));
            userCourseMapper.delete(new LambdaQueryWrapper<UserCourse>().in(UserCourse::getCourseId,courseIds));
            zoneCourseMapper.delete(new LambdaQueryWrapper<ZoneCourse>().in(ZoneCourse::getCourseId,courseIds));
            baseMapper.delete(new LambdaQueryWrapper<Course>().in(Course::getLecturerId,lecturedId));
        }
    }


    private boolean check(CourseChapterPeriod courseChapterPeriod) {
        UserCourse userCourse = userCourseMapper.selectOne(new LambdaQueryWrapper<UserCourse>().eq(UserCourse::getUserId, ThreadContextUtil.userId()).eq(UserCourse::getCourseId, courseChapterPeriod.getCourseId()));
        if (userCourse==null){
            //是否是免费课程
            if (FreeEnum.FREE.getCode().equals(courseChapterPeriod.getIsFree())){
                //增加记录
                UserCourse userCourse1 = new UserCourse();
                userCourse1.setUserId(ThreadContextUtil.userId());
                userCourse1.setCourseId(courseChapterPeriod.getCourseId());
                userCourse1.setBuyType(BuyTypeEnum.FREE.getCode());
                userCourseMapper.insert(userCourse1);
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 课程id获取所有章节
     *
     * @param id
     * @param userStudyProgressMap
     * @return
     */
    private List<CourseChapterResp> getCourseChapterResp(Long courseId, Map<Long, BigDecimal> userStudyProgressMap) {
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(new LambdaQueryWrapper<CourseChapter>().eq(CourseChapter::getCourseId, courseId));
        if (!CollUtil.isEmpty(courseChapters)){
            List<CourseChapterResp> courseChapterResps = BeanUtil.copyToList(courseChapters, CourseChapterResp.class);
            Map<Long, List<CourseChapterPeriodResp>> courseChapterPeriodRespMap = getCourseChapterPeriodRespMap(courseChapterResps, courseId,userStudyProgressMap);
            if (!MapUtil.isEmpty(courseChapterPeriodRespMap)){
                for (CourseChapterResp courseChapterResp:courseChapterResps){
                    courseChapterResp.setPeriodRespList(courseChapterPeriodRespMap.get(courseChapterResp.getId()));
                }
            }
            return courseChapterResps;
        }
        return null;
    }

    /**
     * 获取所有课时
     *
     * @param courseChapterResps
     * @param courseId
     * @param userStudyProgressMap
     * @return
     */
    private Map<Long, List<CourseChapterPeriodResp>> getCourseChapterPeriodRespMap(List<CourseChapterResp> courseChapterResps, Long courseId, Map<Long, BigDecimal> userStudyProgressMap) {
        List<CourseChapterPeriod> courseChapterPeriods = courseChapterPeriodMapper.selectList(new LambdaQueryWrapper<CourseChapterPeriod>().eq(CourseChapterPeriod::getCourseId, courseId));
        if (!CollUtil.isEmpty(courseChapterPeriods)){
            List<CourseChapterPeriodResp> courseChapterPeriodResps = BeanUtil.copyToList(courseChapterPeriods, CourseChapterPeriodResp.class);
            Map<Long, ResourceResp> resourceRespMap = getResourceRespMap(courseChapterPeriodResps);
            if (!MapUtil.isEmpty(resourceRespMap)){
                for (CourseChapterPeriodResp courseChapterPeriodResp:courseChapterPeriodResps){
                    courseChapterPeriodResp.setResourceResp(resourceRespMap.get(courseChapterPeriodResp.getResourceId()));
                    courseChapterPeriodResp.setPeriodProgress(userStudyProgressMap.get(courseChapterPeriodResp.getId()));
                }
            }
            Map<Long, List<CourseChapterPeriodResp>> result = courseChapterPeriodResps.stream().collect(Collectors.groupingBy(CourseChapterPeriodResp::getChapterId, Collectors.toList()));
            return  result;
        }
        return null;
    }

    /**
     * 获取资源
     *
     * @param courseChapterPeriodResps
     * @return
     */
    private Map<Long, ResourceResp> getResourceRespMap(List<CourseChapterPeriodResp> courseChapterPeriodResps) {
        List<com.ninep.course.entity.Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<com.ninep.course.entity.Resource>().in(com.ninep.course.entity.Resource::getId, courseChapterPeriodResps.stream().map(CourseChapterPeriodResp::getResourceId).collect(Collectors.toList())));
        if (!CollUtil.isEmpty(resources)){
            List<ResourceResp> resourceResps = BeanUtil.copyToList(resources, ResourceResp.class);
            Map<Long, ResourceResp> resourceRespMap = resourceResps.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
            return resourceRespMap;
        }
        return null;
    }

    private List<Long> listCategoryId(Long categoryId) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatusId, StatusIdEnum.YES.getCode());
        List<Category> categories = categoryMapper.selectList(wrapper);
        List<Long> idList = new ArrayList<>();
        // 需要查询的ID
        idList.add(categoryId);
        //是否有子类
        filter(idList, categories, categoryId);
        return idList;
    }

    private List<ApiCategoryResp> filter(List<Long> idList, List<Category> categories, Long categoryId) {
        List<Category> list = categories.stream().filter(item -> item.getParentId().compareTo(categoryId) == 0).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(list)) {
            idList.addAll(list.stream().map(Category::getId).collect(Collectors.toList()));
            List<ApiCategoryResp> resps = BeanUtil.copyToList(list, ApiCategoryResp.class);
            for (ApiCategoryResp resp : resps) {
                resp.setList(filter(idList, categories, resp.getId()));
            }
            return resps;
        }
        return new ArrayList<>();
    }


    private static String courseResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }
}
