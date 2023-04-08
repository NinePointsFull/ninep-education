package com.ninep.user.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.user.api.DTO.ApiRegisterDTO;
import com.ninep.user.api.DTO.ApiSendCodeDTO;
import com.ninep.user.api.resonse.ApiUsersLoginResp;
import com.ninep.user.api.DTO.ApiLoginDTO;
import com.ninep.user.service.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户基本信息
 *
 * @author NineP
 */
@Api(tags = "Api-用户登录注册")
@RestController
@RequestMapping(value = "/user/api/users")
public class ApiUsersController {

    @Resource
    private IUsersService usersService;

    /**
     * 注册验证码发送接口
     */
    @ApiOperation(value = "注册验证码发送接口", notes = "发送手机验证码")
    @PostMapping(value = "/send/code")
    public Result<String> sendCode(@RequestBody ApiSendCodeDTO apiSendCodeDTO) {
        return Result.success(usersService.sendCode(apiSendCodeDTO));
    }


    /**
     * 注册接口
     */
    @ApiOperation(value = "注册接口", notes = "注册成功返回登录信息")
    @PostMapping(value = "/register")
    public Result<ApiUsersLoginResp> register(@RequestBody @Validated ApiRegisterDTO apiRegisterDTO) {
        return Result.success(usersService.register(apiRegisterDTO));
    }

    /**
     * 用户密码登录接口
     */
    @ApiOperation(value = "登录接口", notes = "密码登录")
    @PostMapping(value = "/login")
    public Result<ApiUsersLoginResp> loginPassword(@RequestBody @Validated ApiLoginDTO loginVo) {
        ApiUsersLoginResp apiUsersLoginResp=usersService.login(loginVo);
        return Result.success(apiUsersLoginResp);
    }




}
