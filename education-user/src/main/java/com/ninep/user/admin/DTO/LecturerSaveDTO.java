package com.ninep.user.admin.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * ADMIN-讲师信息
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LecturerSaveDTO", description = "ADMIN-讲师信息添加")
public class LecturerSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态(1:正常，0:禁用)")
    private Integer statusId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "讲师名称")
    @NotEmpty(message = "讲师名称不能为空")
    private String lecturerName;

    @ApiModelProperty(value = "讲师手机")
    private String lecturerMobile;

    @ApiModelProperty(value = "讲师职位")
    @NotEmpty(message = "讲师职位不能为空")
    private String lecturerPosition;

    @ApiModelProperty(value = "讲师头像")
    private String lecturerHead;

    @ApiModelProperty(value = "简介")
    @NotEmpty(message = "简介不能为空")
    private String introduce;
}
