package com.ninep.course.auth.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.auth.response.AuthUserCourseResp;
import com.ninep.course.auth.vo.AuthUserCourseVo;
import com.ninep.course.service.IUserCourseService;
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
 * AUTH-课程用户关联表
 *
 * @author NineP
 */
@Api(tags = "AUTH-课程用户关联表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/auth/user/course")
public class AuthUserCourseController {

    @Resource
    private IUserCourseService userCourseService;

    /**
     * 课程信息列表接口
     *
     * @author fengyw
     */
    @ApiOperation(value = "我的课程分页", notes = "根据条件进行课程分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<Page<AuthUserCourseResp>> listForPage(@RequestBody AuthUserCourseVo userCourseVo) {
        Page<AuthUserCourseResp> authUserCourseRespPage=userCourseService.listForPage(userCourseVo);

        return Result.success(authUserCourseRespPage);
    }

}
