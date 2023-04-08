package com.ninep.course.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.ZoneSaveDTO;
import com.ninep.course.admin.response.ZonePageResp;
import com.ninep.course.service.IZoneService;
import com.ninep.course.admin.DTO.ZoneEditDTO;
import com.ninep.course.admin.DTO.ZonePageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 专区 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-专区")
@RestController
@RequestMapping("/course/admin/zone")
public class ZoneController {

    @Resource
    private IZoneService zoneService;

    @ApiOperation(value = "专区分页", notes = "专区分页")
    @PostMapping(value = "/page")
    public Result<Page<ZonePageResp>> page(@RequestBody ZonePageDTO pageVo) {
        Page<ZonePageResp> page=zoneService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "专区添加", notes = "专区添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody ZoneSaveDTO saveVo) {
        return Result.success(zoneService.saveZone(saveVo));
    }

    @ApiOperation(value = "专区修改", notes = "专区修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody ZoneEditDTO zoneEditDTO) {
        return Result.success(zoneService.edit(zoneEditDTO));
    }

    @ApiOperation(value = "专区删除", notes = "专区删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(zoneService.delete(id));
    }
}
