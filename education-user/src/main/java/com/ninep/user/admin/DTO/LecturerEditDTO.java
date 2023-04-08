package com.ninep.user.admin.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-讲师信息
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LecturerEditDTO", description = "ADMIN-讲师信息修改")
public class LecturerEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态(1:正常，0:禁用)")
    private Integer statusId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "讲师名称")
    private String lecturerName;

    @ApiModelProperty(value = "讲师手机")
    private String lecturerMobile;

    @ApiModelProperty(value = "讲师职位")
    private String lecturerPosition;

    @ApiModelProperty(value = "讲师头像")
    private String lecturerHead;

    @ApiModelProperty(value = "简介")
    private String introduce;
}