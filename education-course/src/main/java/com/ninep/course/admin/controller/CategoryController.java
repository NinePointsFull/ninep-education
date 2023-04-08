package com.ninep.course.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CategoryEditDTO;
import com.ninep.course.admin.DTO.CategoryListDTO;
import com.ninep.course.admin.response.CategoryListResp;
import com.ninep.course.service.ICategoryService;
import com.ninep.course.admin.DTO.CategorySaveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 分类 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-分类")
@RestController
@RequestMapping("/course/admin/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @ApiOperation(value = "分类分页", notes = "分类分页")
    @PostMapping(value = "/list")
    public Result<List<CategoryListResp>> list(@RequestBody CategoryListDTO listVo) {
        List<CategoryListResp> list=categoryService.listByCondition(listVo);
        return Result.success(list);
    }

    @ApiOperation(value = "分类添加", notes = "分类添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody CategorySaveDTO saveVo) {
        return Result.success(categoryService.saveCategory(saveVo));
    }

    @ApiOperation(value = "分类修改", notes = "分类修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid CategoryEditDTO editVo) {
        return Result.success(categoryService.edit(editVo));
    }

    @ApiOperation(value = "分类删除", notes = "分类删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return Result.success(categoryService.delete(id));
    }

}
