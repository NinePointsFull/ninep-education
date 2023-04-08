package com.ninep.course.service;

import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.common.utils.Page;
import com.ninep.course.auth.response.AuthUserCourseResp;
import com.ninep.course.auth.vo.AuthUserCourseVo;
import com.ninep.course.entity.UserCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程用户关联表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IUserCourseService extends IService<UserCourse> {

    Page<AuthUserCourseResp> listForPage(AuthUserCourseVo userCourseVo);

    int binding(UserCourseBindingDTO bindingDTO);
}
