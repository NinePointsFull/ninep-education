package com.ninep.course.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.response.ZoneCoursePageResp;
import com.ninep.course.service.IZoneCourseService;
import com.ninep.course.admin.DTO.ZoneCourseEditDTO;
import com.ninep.course.admin.DTO.ZoneCoursePageDTO;
import com.ninep.course.admin.DTO.ZoneCourseSaveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 专区课程关联表 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-专区课程关联表")
@RestController
@RequestMapping("/course/admin/zone/course")
public class ZoneCourseController {

    @Resource
    private IZoneCourseService zoneCourseService;


    @ApiOperation(value = "专区课程关联表分页", notes = "专区课程关联表分页")
    @PostMapping(value = "/page")
    public Result<Page<ZoneCoursePageResp>> page(@RequestBody ZoneCoursePageDTO pageVo) {
        Page<ZoneCoursePageResp> page=zoneCourseService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "专区课程关联表添加", notes = "专区课程关联表添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody ZoneCourseSaveDTO zoneCourseSaveDTO) {
        return Result.success(zoneCourseService.saveRelation(zoneCourseSaveDTO));
    }

    @ApiOperation(value = "专区课程关联表删除", notes = "专区课程关联表删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(zoneCourseService.delete(id));
    }

    @ApiOperation(value = "专区课程关联表修改", notes = "专区课程关联表修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody ZoneCourseEditDTO editVo) {
        return Result.success(zoneCourseService.edit(editVo));
    }


}
