package com.ninep.course.admin.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-专区课程关联表
 * </p>
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ZoneCourseEditDTO", description = "ADMIN-专区课程关联表修改")
public class ZoneCourseEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态(1:正常;0:禁用)")
    private Integer statusId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "专区编号")
    private Long zoneId;

    @ApiModelProperty(value = "课程ID")
    private Long courseId;
}
