package com.ninep.system.admin.controller;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteCarouselEditDTO;
import com.ninep.system.admin.dto.SysWebsiteCarouselPageDTO;
import com.ninep.system.admin.response.SysWebsiteCarouselPageResp;
import com.ninep.system.admin.dto.SysWebsiteCarouselSaveDTO;
import com.ninep.system.service.IWebsiteCarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 广告信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Api(tags = "ADMIN-广告信息")
@RestController
@RequestMapping("/system/admin/website/carousel")
public class WebsiteCarouselController {
    @Resource
    private IWebsiteCarouselService websiteCarouselService;

    @ApiOperation(value = "广告信息分页", notes = "广告信息分页")
    @PostMapping("/page")
    public Result<Page<SysWebsiteCarouselPageResp>> page(@RequestBody SysWebsiteCarouselPageDTO pageVo){
        Page<SysWebsiteCarouselPageResp> respPage=websiteCarouselService.pageForCondition(pageVo);
        return Result.success(respPage);
    }

    @ApiOperation(value = "广告信息修改", notes = "广告信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody SysWebsiteCarouselEditDTO editDTO) {
        String result=websiteCarouselService.edit(editDTO);
        return Result.success(result);
    }
    @ApiOperation(value = "广告信息删除", notes = "广告信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(websiteCarouselService.deleteById(id));
    }


    @ApiOperation(value = "广告信息添加", notes = "广告信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody SysWebsiteCarouselSaveDTO sysWebsiteCarouselSaveDTO) {
        return Result.success(websiteCarouselService.save(sysWebsiteCarouselSaveDTO));
    }
}
