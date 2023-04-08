package com.ninep.course.admin.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-分类
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ACategorySaveVo", description = "ADMIN-分类添加")
public class CategorySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态(1:正常，0:禁用)")
    private Integer statusId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "父分类ID")
    private Long parentId;

    @ApiModelProperty(value = "类型(1课程，2资源)")
    private Integer categoryType;

    @ApiModelProperty(value = "名称")
    private String categoryName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
