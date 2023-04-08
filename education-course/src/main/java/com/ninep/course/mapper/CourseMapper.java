package com.ninep.course.mapper;

import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface CourseMapper extends BaseMapper<Course> {

    void addCountBuy(@Param("item") UserCourseBindingDTO bindingDTO);

    void addCountStudy(@Param("item") UserCourseBindingDTO bindingDTO);
}
