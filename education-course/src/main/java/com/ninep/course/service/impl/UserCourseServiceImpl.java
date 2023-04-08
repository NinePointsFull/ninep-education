package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.common.enums.BuyTypeEnum;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.ThreadContextUtil;
import com.ninep.course.auth.response.AuthCourseResp;
import com.ninep.course.auth.response.AuthUserCourseResp;
import com.ninep.course.auth.vo.AuthUserCourseVo;
import com.ninep.course.entity.Course;
import com.ninep.course.entity.CourseChapterPeriod;
import com.ninep.course.entity.UserCourse;
import com.ninep.course.entity.UserStudy;
import com.ninep.course.mapper.CourseChapterPeriodMapper;
import com.ninep.course.mapper.CourseMapper;
import com.ninep.course.mapper.UserCourseMapper;
import com.ninep.course.mapper.UserStudyMapper;
import com.ninep.course.service.IUserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程用户关联表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements IUserCourseService {
    @Resource
    private UserStudyMapper userStudyMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseChapterPeriodMapper courseChapterPeriodMapper;

    @Override
    public Page<AuthUserCourseResp> listForPage(AuthUserCourseVo userCourseVo) {

        //根据用户id查询用户课程
        IPage<UserCourse> userCourseIPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(userCourseVo.getPageCurrent(),userCourseVo.getPageSize());
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId, ThreadContextUtil.userId());
        this.page(userCourseIPage,wrapper);
        Page<UserCourse> parse = PageUtil.parse(userCourseIPage);
        Page<AuthUserCourseResp> transform = PageUtil.transform(parse, AuthUserCourseResp.class);
        if (!CollUtil.isEmpty(transform.getList())){
            //封装
            //TODO 业务逻辑？
            authUserCourseRespResult(transform.getList());
        }
        return transform;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int binding(UserCourseBindingDTO bindingDTO) {
        UserCourse record = BeanUtil.copyProperties(bindingDTO, UserCourse.class);
        if (this.save(record)) {
            if (record.getBuyType().equals(BuyTypeEnum.BUY.getCode())) {
                // 课程购买数+1
                courseMapper.addCountBuy(bindingDTO);
            }
            courseMapper.addCountStudy(bindingDTO);
            return 1;
        }
        return 0;
    }

    private void authUserCourseRespResult(List<AuthUserCourseResp> list) {
            List<Long> courseIdList = list.stream().map(AuthUserCourseResp::getCourseId).collect(Collectors.toList());
            // 用户学习记录，获取每个课程里面最新学习的课时
            Map<Long, UserStudy> userStudyMap = new HashMap<>();
            List<UserStudy> userStudyList = userStudyMapper.listByUserIdAndCourseIdsForMax(ThreadContextUtil.userId(),courseIdList);
            if (CollUtil.isNotEmpty(userStudyList)) {
                userStudyMap = userStudyList.stream().collect(Collectors.toMap(item -> item.getCourseId(), item -> item));
            }

            // 课时名称
            Map<Long, String> periodNameMap = new HashMap<>();
            // 每个课程的课时数
            Map<Long, Long> periodSumMap = new HashMap<>();
            List<CourseChapterPeriod> courseChapterPeriodList = courseChapterPeriodMapper.selectList(new LambdaQueryWrapper<CourseChapterPeriod>().in(CourseChapterPeriod::getCourseId,courseIdList));
            if (CollUtil.isNotEmpty(courseChapterPeriodList)) {
                periodNameMap = courseChapterPeriodList.stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getPeriodName()));
                periodSumMap = courseChapterPeriodList.stream().collect(Collectors.groupingBy(item -> item.getCourseId(), Collectors.counting()));
            }

            // 每个课程的学习进度汇总
            Map<Long, BigDecimal> userStudySumMap = new HashMap<>();
            List<UserStudy> userStudySumList = userStudyMapper.listByUserIdAndCourseIdsForSumProgress(ThreadContextUtil.userId(), courseIdList);
            if (CollUtil.isNotEmpty(userStudySumList)) {
                userStudySumMap = userStudySumList.stream().collect(Collectors.toMap(item -> item.getCourseId(), item -> item.getProgress()));
            }

            // 课程信息
            List<Course> courseList = courseMapper.selectBatchIds(courseIdList);
            Map<Long, Course> courseMap = courseList.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

            for (AuthUserCourseResp resp : list) {
                UserStudy userStudy = userStudyMap.get(resp.getCourseId());
                if (ObjectUtil.isNotEmpty(userStudy)) {
                    resp.setPeriodProgress(userStudy.getProgress());
                    resp.setPeriodTime(userStudy.getGmtModified());
                    resp.setPeriodName(periodNameMap.get(userStudy.getPeriodId()));
                    resp.setCourseProgress(userStudySumMap.get(resp.getCourseId()).divide(BigDecimal.valueOf(periodSumMap.get(resp.getCourseId())), BigDecimal.ROUND_UP));
                }
                resp.setCourseResp(BeanUtil.copyProperties(courseMap.get(resp.getCourseId()), AuthCourseResp.class));
            }


    }
}
