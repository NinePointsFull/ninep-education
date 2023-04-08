package com.ninep.course.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.course.api.response.ApiCategoryResp;
import com.ninep.course.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程分类
 *
 * @author NineP
 */
@RestController
@RequestMapping(value = "/course/api/category")
public class ApiCategoryController {

    @Resource
    private ICategoryService categoryService;


    @ApiOperation(value = "课程分类列表接口", notes = "课程分类列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<ApiCategoryResp>> list() {
        return Result.success(categoryService.listForCategory());
    }

}
