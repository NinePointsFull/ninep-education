package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterPageDTO;
import com.ninep.course.admin.DTO.CourseChapterSaveDTO;
import com.ninep.course.admin.response.CourseChapterPageResp;
import com.ninep.course.admin.response.CourseChapterPeriodViewResp;
import com.ninep.course.admin.response.ResourceViewResp;
import com.ninep.course.admin.DTO.CourseChapterEditDTO;
import com.ninep.course.entity.CourseChapter;
import com.ninep.course.entity.CourseChapterPeriod;
import com.ninep.course.entity.UserStudy;
import com.ninep.course.mapper.CourseChapterMapper;
import com.ninep.course.mapper.CourseChapterPeriodMapper;
import com.ninep.course.mapper.ResourceMapper;
import com.ninep.course.mapper.UserStudyMapper;
import com.ninep.course.service.ICourseChapterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 章节信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {

    @Resource
    private CourseChapterPeriodMapper courseChapterPeriodMapper;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private UserStudyMapper userStudyMapper;

    @Override
    public Page<CourseChapterPageResp> pageList(CourseChapterPageDTO pageVo) {
        IPage<CourseChapter> courseChapterIPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<CourseChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CourseChapter::getSort).orderByAsc(CourseChapter::getId);
        wrapper.eq(CourseChapter::getCourseId,pageVo.getCourseId());
        this.page(courseChapterIPage,wrapper);
        Page<CourseChapter> parse = PageUtil.parse(courseChapterIPage);
        Page<CourseChapterPageResp> transform = PageUtil.transform(parse, CourseChapterPageResp.class);
        extracted(transform);
        return transform;
    }

    @Override
    public Result<String> saveChapter(CourseChapterSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, SysConfigEnum.CONFIG_ERROR);
        CourseChapter courseChapter = BeanUtil.copyProperties(saveVo, CourseChapter.class);
        if (this.save(courseChapter)){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    public Result<String> edit(CourseChapterEditDTO editVo) {
        AssertUtil.notNull(editVo,SysConfigEnum.CONFIG_ERROR);
        AssertUtil.notNull(editVo.getId(),SysConfigEnum.CONFIG_ERROR);
        CourseChapter courseChapter = BeanUtil.copyProperties(editVo, CourseChapter.class);
        if (this.updateById(courseChapter)){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }

        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> delete(Long id) {
        AssertUtil.notNull(id,SysConfigEnum.CONFIG_ERROR);
        courseChapterPeriodMapper.delete(new LambdaQueryWrapper<CourseChapterPeriod>().eq(CourseChapterPeriod::getChapterId,id));
        userStudyMapper.delete(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getChapterId,id));
        if (this.removeById(id)){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    /**
     * 封装章节
     * @param transform
     */
    private void extracted(Page<CourseChapterPageResp> transform) {
        if (!CollectionUtils.isEmpty(transform.getList())){
            //获取章节下所有的课时
            List<Long> chapterIds = transform.getList().stream().map(CourseChapterPageResp::getId).collect(Collectors.toList());
            List<CourseChapterPeriod> chapterPeriods = courseChapterPeriodMapper.selectList(new LambdaQueryWrapper<CourseChapterPeriod>().in(CourseChapterPeriod::getChapterId, chapterIds));
            if (!CollectionUtils.isEmpty(chapterPeriods)){
                List<CourseChapterPeriodViewResp> courseChapterPeriodViewResps = BeanUtil.copyToList(chapterPeriods, CourseChapterPeriodViewResp.class);
                //获取课时下的所有资源
                List<Long> resourceIds = chapterPeriods.stream().map(CourseChapterPeriod::getResourceId).collect(Collectors.toList());
                List<com.ninep.course.entity.Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<com.ninep.course.entity.Resource>().in(com.ninep.course.entity.Resource::getId, resourceIds));
                if (!CollUtil.isEmpty(resources)){
                    List<ResourceViewResp> resourceViewResps = BeanUtil.copyToList(resources, ResourceViewResp.class);
                    Map<Long, ResourceViewResp> resourceViewRespMap = resourceViewResps.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
                    //封装资源
                    for (CourseChapterPeriodViewResp courseChapterPeriodViewResp:courseChapterPeriodViewResps){
                        courseChapterPeriodViewResp.setResourceViewResp(resourceViewRespMap.get(courseChapterPeriodViewResp.getResourceId()));
                    }
                }
                Map<Long, List<CourseChapterPeriodViewResp>> collect = courseChapterPeriodViewResps.stream().collect(Collectors.groupingBy(CourseChapterPeriodViewResp::getChapterId, Collectors.toList()));
                for (CourseChapterPageResp courseChapterPageResp:transform.getList()){
                    courseChapterPageResp.setPeriodViewRespList(collect.get(courseChapterPageResp.getId()));
                }
            }
        }
    }
}
