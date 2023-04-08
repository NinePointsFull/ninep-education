package com.ninep.course.api.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.api.response.ApiCoursePageResp;
import com.ninep.course.common.response.CourseResp;
import com.ninep.course.api.vo.ApiCoursePageVo;
import com.ninep.course.common.vo.CourseVo;
import com.ninep.course.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * API-课程信息
 *
 * @author NineP
 */
@Api(tags = "API-课程信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/api/course")
public class ApiCourseController {

    @Resource
    private ICourseService courseService;


    @ApiOperation(value = "列表和搜索接口", notes = "根据条件进行课程列出")
    @PostMapping(value = "/search")
    public Result<Page<ApiCoursePageResp>> searchForPage(@RequestBody ApiCoursePageVo pageVo) {
        Page<ApiCoursePageResp> coursePageResp=courseService.searchForPage(pageVo);
        return Result.success(coursePageResp);
    }

    /**
     * 没有登入的入口
     * 课程详情接口
     */
    @ApiOperation(value = "课程详情接口", notes = "根据课程ID获取课程信息")
    @PostMapping(value = "/view")
    public Result<CourseResp> view(@RequestBody CourseVo courseVo) {
        CourseResp courseResp =courseService.view(courseVo,null);
        return Result.success(courseResp);
    }
}
