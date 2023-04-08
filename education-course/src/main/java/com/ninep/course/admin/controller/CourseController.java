package com.ninep.course.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ninep.common.DTO.CourseViewDTO;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseEditDTO;
import com.ninep.course.admin.DTO.CourseSaveDTO;
import com.ninep.course.admin.response.CoursePageResp;
import com.ninep.course.admin.DTO.CoursePageDTO;
import com.ninep.course.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-课程信息")
@RestController
@RequestMapping("/course/admin/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @ApiOperation(value = "课程信息分页", notes = "课程信息分页")
    @PostMapping(value = "/page")
    public Result<Page<CoursePageResp>> page(@RequestBody CoursePageDTO pageVo) {
        Page<CoursePageResp> page=courseService.pageList(pageVo);
        return Result.success(page);
    }


    @ApiOperation(value = "课程信息删除", notes = "课程信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(courseService.delete(id));
    }

    @ApiOperation(value = "课程信息添加", notes = "课程信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody CourseSaveDTO courseSaveDTO) {
        return Result.success(courseService.saveCourse(courseSaveDTO));
    }


    @ApiOperation(value = "课程信息修改", notes = "课程信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody CourseEditDTO courseEditDTO) {
        return Result.success(courseService.edit(courseEditDTO));
    }

    @GetMapping("/view")
    public List<CourseViewDTO> view(@RequestParam("courseIds") List<Long> courseIds){
        return courseService.listByIds(courseIds).stream().map(item->{
            CourseViewDTO courseViewDTO = new CourseViewDTO();
            BeanUtil.copyProperties(item,courseViewDTO);
            return courseViewDTO;
        }).collect(Collectors.toList());
    }


    @DeleteMapping("/deleteByLecturerId")
    public Result<Boolean> deleteByLecturerId(@RequestParam("lecturerId") Long lecturedId){
        courseService.deleteByLecturerId(lecturedId);
        return Result.success(true);
    }



}
