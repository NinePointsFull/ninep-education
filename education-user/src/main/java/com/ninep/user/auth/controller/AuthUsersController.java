package com.ninep.user.auth.controller;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.ninep.common.utils.Result;
import com.ninep.user.auth.response.AuthUsersResp;
import com.ninep.user.service.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * AUTH-用户信息
 *
 * @author NineP
 */
@Api(tags = "AUTH-用户信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth/users")
public class AuthUsersController {

    @Resource
    private IUsersService usersService;

    /**
     * 用户信息查看接口
     */
    @ApiOperation(value = "查看接口", notes = "获取当前用户的基本信息")
    @GetMapping(value = "/view")
    public Result<AuthUsersResp> view() {
        AuthUsersResp authUsersResp=usersService.view();
        return Result.success(authUsersResp);
    }


}
