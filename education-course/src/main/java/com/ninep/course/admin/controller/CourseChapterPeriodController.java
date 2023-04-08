package com.ninep.course.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterPeriodEditDTO;
import com.ninep.course.admin.DTO.CourseChapterPeriodSaveDTO;
import com.ninep.course.service.ICourseChapterPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课时信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-课时信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/course/chapter/period")
public class CourseChapterPeriodController {

    @Resource
    private ICourseChapterPeriodService courseChapterPeriodService;

    @ApiOperation(value = "课时信息添加", notes = "课时信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody CourseChapterPeriodSaveDTO saveVo) {
        return courseChapterPeriodService.savePeriod(saveVo);
    }

    @ApiOperation(value = "课时信息修改", notes = "课时信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody CourseChapterPeriodEditDTO editVo) {
        return courseChapterPeriodService.edit(editVo);
    }

    @ApiOperation(value = "课时信息删除", notes = "课时信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return courseChapterPeriodService.delete(id);
    }

}
