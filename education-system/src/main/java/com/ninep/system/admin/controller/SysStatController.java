package com.ninep.system.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.admin.response.SysStatVodResp;
import com.ninep.system.service.ISysStatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 点播直播统计
 *
 */
@RestController
@RequestMapping("/system/admin/stat")
@Api(value = "system-点播直播统计", tags = {"system-点播直播统计"})
public class SysStatController {

    @Resource
    private ISysStatService sysStatService;

    @ApiOperation(value = "点播", notes = "点播空间和流量的统计")
    @GetMapping(value = "/vod")
    public Result<SysStatVodResp> vod() {
        SysStatVodResp sysStatVodResp=sysStatService.vod();

        return Result.success(sysStatVodResp);
    }

}
