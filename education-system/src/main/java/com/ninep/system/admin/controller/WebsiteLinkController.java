package com.ninep.system.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteLinkEditDTO;
import com.ninep.system.admin.dto.SysWebsiteLinkPageDTO;
import com.ninep.system.admin.dto.SysWebsiteLinkSaveDTO;
import com.ninep.system.admin.response.SysWebsiteLinkPageResp;
import com.ninep.system.service.IWebsiteLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 站点友情链接 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Api(tags = "ADMIN-站点友情链接")
@RestController
@RequestMapping("/system/admin/website/link")
public class WebsiteLinkController {
    @Resource
    private IWebsiteLinkService websiteLinkService;

    @ApiOperation(value = "站点友情链接分页", notes = "站点友情链接分页")
    @PostMapping(value = "/page")
    public Result<Page<SysWebsiteLinkPageResp>> page(@RequestBody SysWebsiteLinkPageDTO pageVo) {
        Page<SysWebsiteLinkPageResp> page=websiteLinkService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "站点友情链接添加", notes = "站点友情链接添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Valid SysWebsiteLinkSaveDTO saveVo) {
        return Result.success(websiteLinkService.saveLink(saveVo));
    }
    @ApiOperation(value = "站点友情链接修改", notes = "站点友情链接修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody SysWebsiteLinkEditDTO editVo) {
        return Result.success(websiteLinkService.editLink(editVo));
    }


    @ApiOperation(value = "站点友情链接删除", notes = "站点友情链接删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(websiteLinkService.delete(id));
    }
}
