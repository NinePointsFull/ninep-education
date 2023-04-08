package com.ninep.course.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.course.api.response.ApiZoneResp;
import com.ninep.course.service.IZoneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专区课程关联表
 *
 * @author NineP
 */
@RestController
@RequestMapping(value = "/course/api/zone")
public class ApiZoneController {

    @Resource
    private IZoneService zoneService;



    @ApiOperation(value = "专区接口", notes = "列出专区课程列表")
    @PostMapping(value = "/list")
    public Result<List<ApiZoneResp>> list() {
        return Result.success(zoneService.listForZone());
    }

}
