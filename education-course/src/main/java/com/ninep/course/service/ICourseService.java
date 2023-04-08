package com.ninep.course.service;

import com.ninep.common.utils.Page;
import com.ninep.course.api.response.ApiCoursePageResp;
import com.ninep.course.api.vo.AuthCourseSignVo;
import com.ninep.course.auth.response.AuthCourseSignResp;
import com.ninep.course.common.response.CourseResp;
import com.ninep.course.api.vo.ApiCoursePageVo;
import com.ninep.course.common.vo.CourseVo;
import com.ninep.course.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.course.admin.response.CoursePageResp;
import com.ninep.course.admin.DTO.CourseEditDTO;
import com.ninep.course.admin.DTO.CoursePageDTO;
import com.ninep.course.admin.DTO.CourseSaveDTO;

/**
 * <p>
 * 课程信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface ICourseService extends IService<Course> {

    Page<CoursePageResp> pageList(CoursePageDTO pageVo);

    String delete(Long id);

    String saveCourse(CourseSaveDTO saveVo);

    String edit(CourseEditDTO editVo);

    Page<ApiCoursePageResp> searchForPage(ApiCoursePageVo pageVo);

    CourseResp view(CourseVo courseVo, Long userId);

    AuthCourseSignResp sign(AuthCourseSignVo signVo);

    void deleteByLecturerId(Long lecturedId);
}
