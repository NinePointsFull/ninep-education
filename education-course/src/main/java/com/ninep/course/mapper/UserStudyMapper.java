package com.ninep.course.mapper;

import com.ninep.course.entity.UserStudy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程用户学习日志 Mapper 接口
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface UserStudyMapper extends BaseMapper<UserStudy> {

    List<UserStudy> listByUserIdAndCourseIdsForMax(@Param("userId") Long userId, @Param("courseIds") List<Long> courseIdList);

    List<UserStudy> listByUserIdAndCourseIdsForSumProgress(@Param("userId") Long userId, @Param("courseIds") List<Long> courseIdList);
}
