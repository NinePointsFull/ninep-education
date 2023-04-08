package com.ninep.system.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.api.response.ApiSysConfigWebsiteResp;
import com.ninep.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author NineP
 */
@Api(tags = "AUTH-系统配置")
@Slf4j
@RestController
@RequestMapping("/system/api/sys/config")
public class ApiSystemConfigController {
    @Resource
    private ISysConfigService sysConfigService;

    @ApiOperation(value = "网站基本信息", notes = "网站基本信息")
    @GetMapping("/website")
    public Result<ApiSysConfigWebsiteResp> websiteBase(){
        ApiSysConfigWebsiteResp apiSysConfigWebsiteResp =sysConfigService.websiteBase();
        return Result.success(apiSysConfigWebsiteResp);
    }
}
