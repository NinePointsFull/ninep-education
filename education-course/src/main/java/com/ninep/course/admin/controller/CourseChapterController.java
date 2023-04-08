package com.ninep.course.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterPageDTO;
import com.ninep.course.admin.response.CourseChapterPageResp;
import com.ninep.course.admin.DTO.CourseChapterEditDTO;
import com.ninep.course.admin.DTO.CourseChapterSaveDTO;
import com.ninep.course.service.ICourseChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 章节信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-章节信息")
@RestController
@RequestMapping("/course/admin/course/chapter")
public class CourseChapterController {

    @Resource
    private ICourseChapterService courseChapterService;

    @ApiOperation(value = "章节信息分页", notes = "章节信息分页")
    @PostMapping(value = "/page")
    public Result<Page<CourseChapterPageResp>> page(@RequestBody CourseChapterPageDTO pageVo) {
        Page<CourseChapterPageResp> page=courseChapterService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "章节信息添加", notes = "章节信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody CourseChapterSaveDTO saveVo) {
        return courseChapterService.saveChapter(saveVo);
    }


    @ApiOperation(value = "章节信息修改", notes = "章节信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody CourseChapterEditDTO editVo) {

        return courseChapterService.edit(editVo);
    }

    @ApiOperation(value = "章节信息删除", notes = "章节信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return courseChapterService.delete(id);
    }

}
