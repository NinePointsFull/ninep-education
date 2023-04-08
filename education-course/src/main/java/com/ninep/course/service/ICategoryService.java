package com.ninep.course.service;

import com.ninep.course.api.response.ApiCategoryResp;
import com.ninep.course.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.course.admin.response.CategoryListResp;
import com.ninep.course.admin.DTO.CategoryEditDTO;
import com.ninep.course.admin.DTO.CategoryListDTO;
import com.ninep.course.admin.DTO.CategorySaveDTO;

import java.util.List;

/**
 * <p>
 * 分类 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface ICategoryService extends IService<Category> {

    List<CategoryListResp> listByCondition(CategoryListDTO listVo);

    String saveCategory(CategorySaveDTO saveVo);

    String edit(CategoryEditDTO editVo);

    String delete(Long id);

    List<ApiCategoryResp> listForCategory();
}
