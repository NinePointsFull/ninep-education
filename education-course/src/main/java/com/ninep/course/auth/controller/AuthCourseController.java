package com.ninep.course.auth.controller;

import com.ninep.common.utils.Result;
import com.ninep.common.utils.ThreadContextUtil;
import com.ninep.course.api.vo.AuthCourseSignVo;
import com.ninep.course.auth.response.AuthCourseSignResp;
import com.ninep.course.common.response.CourseResp;
import com.ninep.course.common.vo.CourseVo;
import com.ninep.course.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * AUTH-课程信息
 * @author NineP
 */
@Api(tags = "AUTH-课程信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/auth/course")
public class AuthCourseController {
    @Resource
    private ICourseService courseService;

    /**
     * 课程详情接口
     */
    @ApiOperation(value = "课程详情", notes = "校验课程是否可以学习")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Result<CourseResp> view(@RequestBody CourseVo courseVo) {
        CourseResp view = courseService.view(courseVo, ThreadContextUtil.userId());
        return Result.success(view);
    }


    @ApiOperation(value = "观看授权", notes = "课程观看授权")
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Result<AuthCourseSignResp> sign(@RequestBody AuthCourseSignVo signVo) {
        AuthCourseSignResp authCourseSignResp=courseService.sign(signVo);
        return Result.success(authCourseSignResp);
    }

}
