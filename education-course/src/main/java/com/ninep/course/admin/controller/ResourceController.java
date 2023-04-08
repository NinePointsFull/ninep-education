package com.ninep.course.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.ResourcePageDTO;
import com.ninep.course.admin.response.ResourcePageResp;
import com.ninep.course.admin.response.VodConfigResp;
import com.ninep.course.admin.DTO.ResourceEditDTO;
import com.ninep.course.admin.DTO.ResourceSaveDTO;
import com.ninep.course.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-课程视频信息")
@RestController
@RequestMapping("/course/admin/resource")
@Slf4j
public class ResourceController {
    @Resource
    private IResourceService resourceService;


    @ApiOperation(value = "获取上传音视频参数", notes = "获取上传音视频参数")
    @GetMapping(value = "/vod/config")
    public Result<VodConfigResp> getVodConfig() {
        return resourceService.getVodConfig();
    }


    @ApiOperation(value = "课程视频信息添加", notes = "课程视频信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody ResourceSaveDTO saveVo) {
        return resourceService.saveResource(saveVo);
    }

    @ApiOperation(value = "课程视频信息分页", notes = "课程视频信息分页")
    @PostMapping(value = "/page")
    public Result<Page<ResourcePageResp>> page(@RequestBody ResourcePageDTO pageVo) {
        Page<ResourcePageResp> page=resourceService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "课程视频信息修改", notes = "课程视频信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody ResourceEditDTO editVo) {
        return resourceService.edit(editVo);
    }

    @ApiOperation(value = "课程视频信息删除", notes = "课程视频信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return resourceService.delete(id);
    }
}
