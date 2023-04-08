package com.ninep.user.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.user.api.resonse.StatLoginResp;
import com.ninep.user.service.StatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 点播直播统计
 */
@RestController
@RequestMapping("/user/admin/stat")
@Api(value = "user-用户登录统计", tags = {"user-用户登录统计"})
public class StatController {

    @Autowired
    private StatService statService;

    @ApiOperation(value = "登录统计", notes = "获取最近7天的用户登录情况")
    @GetMapping(value = "/login")
    public Result<StatLoginResp> statLogin(@RequestParam Integer dates) {
        StatLoginResp statLoginResp=statService.statLogin(dates);
        return Result.success(statLoginResp);
    }

}
