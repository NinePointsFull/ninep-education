package com.ninep.course.admin.controller;

import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.course.service.IUserCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 课程用户关联表 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@RestController
@Slf4j
@RequestMapping("/course/admin/userCourse")
public class UserCourseController {

    @Resource
    private IUserCourseService userCourseService;

    @PutMapping(value = "/course/admin/userCourse/binding")
    public int binding(@RequestBody UserCourseBindingDTO bindingDTO){
        return userCourseService.binding(bindingDTO);
    }

}
