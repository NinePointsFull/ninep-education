package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.AssertUtil;
import com.ninep.course.admin.response.CategoryListResp;
import com.ninep.course.admin.DTO.CategoryEditDTO;
import com.ninep.course.admin.DTO.CategoryListDTO;
import com.ninep.course.admin.DTO.CategorySaveDTO;
import com.ninep.course.api.response.ApiCategoryResp;
import com.ninep.course.entity.Category;
import com.ninep.course.mapper.CategoryMapper;
import com.ninep.course.service.ICategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {


    @Override
    public List<CategoryListResp> listByCondition(CategoryListDTO listVo) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getGmtModified);
        wrapper.eq(Category::getCategoryType,listVo.getCategoryType());
        List<Category> list = this.list(wrapper);
        if (CollectionUtils.isEmpty(list)){
            return new ArrayList<CategoryListResp>();
        }
        return this.filter(0L,list);
    }

    @Override
    @CacheEvict(cacheNames = "course",key ="'category'" )
    public String saveCategory(CategorySaveDTO saveVo) {
        AssertUtil.notNull(saveVo,ResultEnum.OTHER_ERROR);
        Category category = BeanUtil.copyProperties(saveVo, Category.class);
        boolean save = this.save(category);
        return categoryResult(save);

    }

    @Override
    @CacheEvict(cacheNames = "course",key ="'category'" )
    public String edit(CategoryEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        AssertUtil.notNull(editVo.getId(),ResultEnum.OTHER_ERROR);
        Category category = BeanUtil.copyProperties(editVo, Category.class);
        if (editVo.getStatusId()!=null){
            //是否存在子类
            List<Category> list = this.list(new LambdaQueryWrapper<Category>().eq(Category::getParentId, editVo.getId()));
            if (!CollectionUtils.isEmpty(list)){
                //同时修改下级分类
                for (Category child:list){
                    child.setStatusId(editVo.getStatusId());
                }
                this.updateBatchById(list);
            }
        }
        boolean b = this.updateById(category);
        return categoryResult(b);
    }

    @Override
    @CacheEvict(cacheNames = "course",key ="'category'" )
    public String delete(Long id) {
        AssertUtil.notNull(id,ResultEnum.OTHER_ERROR);
        //是否存在子类
        List<Category> list = this.list(new LambdaQueryWrapper<Category>().eq(Category::getParentId, id));
        if (!CollectionUtils.isEmpty(list)){
            throw new BaseException("请先删除下级分类");
        }
        boolean b = this.removeById(id);

        return categoryResult(b);
    }

    @Override
    @Cacheable(cacheNames = "course",key ="'category'" ,sync = true)
    public List<ApiCategoryResp> listForCategory() {
        List<Category> categories = this.list(new LambdaQueryWrapper<Category>().eq(Category::getStatusId, StatusIdEnum.YES.getCode()).orderByAsc(Category::getSort).orderByDesc(Category::getGmtModified));
        if (!CollectionUtils.isEmpty(categories)){
            List<ApiCategoryResp> apiCategoryResps=getApiCategoryResps(0L,categories);
            return apiCategoryResps;
        }

        return null;
    }

    /**
     * 封装三级分类
     * @param l
     * @param categories
     * @return
     */
    private List<ApiCategoryResp> getApiCategoryResps(Long parentId, List<Category> categories) {
        List<Category> categoryList = categories.stream().filter(item -> parentId.equals(item.getParentId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(categoryList)){
            List<ApiCategoryResp> apiCategoryResps = BeanUtil.copyToList(categoryList, ApiCategoryResp.class);
            for (ApiCategoryResp apiCategoryResp:apiCategoryResps){
                apiCategoryResp.setList(getApiCategoryResps(apiCategoryResp.getId(),categories));
            }
            return apiCategoryResps;
        }
        return new ArrayList<>();
    }

    private static String categoryResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }


    private List<CategoryListResp> filter(Long parentId, List<Category> list) {
        //获取子类
        List<Category> child = list.stream().filter(item -> item.getParentId().equals(parentId) == true).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)){
            List<CategoryListResp> categoryListResps = BeanUtil.copyToList(child, CategoryListResp.class);
            for (CategoryListResp categoryListResp:categoryListResps){
                categoryListResp.setChildrenList(filter(categoryListResp.getId(),list));
            }
            return categoryListResps;
        }
        return new ArrayList<CategoryListResp>();
    }
}
