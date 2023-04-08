package com.ninep.system.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.admin.response.SysLoginResp;
import com.ninep.system.admin.dto.SysLoginDTO;
import com.ninep.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author NineP
 */
@Slf4j
@RequestMapping("/system/admin/login")
@RestController
@Api(tags = "登录接口")
public class SysLoginController {
    @Resource
    private ISysUserService userService;

    @ApiOperation(value = "密码登录", notes = "用户使用密码登录")
    @PostMapping("/password")
    public Result<SysLoginResp> login(@RequestBody @Validated SysLoginDTO sysLoginDTO){
        SysLoginResp sysLoginResp=userService.login(sysLoginDTO);
        return Result.success(sysLoginResp);
    }

}
