package com.ninep.system.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteNavSaveDTO;
import com.ninep.system.admin.response.SysWebsiteNavPageResp;
import com.ninep.system.admin.dto.SysWebsiteNavEditDTO;
import com.ninep.system.admin.dto.SysWebsiteNavPageDTO;
import com.ninep.system.service.IWebsiteNavService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 头部导航 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Api(tags = "ADMIN-头部导航")
@RestController
@RequestMapping("/system/admin/website/nav")
public class WebsiteNavController {

    @Resource
    private IWebsiteNavService websiteNavService;

    @ApiOperation(value = "头部导航分页", notes = "头部导航分页")
    @PostMapping(value = "/page")
    public Result<Page<SysWebsiteNavPageResp>> page(@RequestBody SysWebsiteNavPageDTO navPageVo) {
        Page<SysWebsiteNavPageResp> page=websiteNavService.pageList(navPageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "头部导航添加", notes = "头部导航添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody SysWebsiteNavSaveDTO saveVo) {
        return Result.success(websiteNavService.saveNav(saveVo));
    }

    @ApiOperation(value = "头部导航修改", notes = "头部导航修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody SysWebsiteNavEditDTO editVo) {
        return Result.success(websiteNavService.editNav(editVo));
    }

    @ApiOperation(value = "头部导航删除", notes = "头部导航删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(websiteNavService.delete(id));
    }

}
