package com.ninep.course.admin.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-课程视频信息
 * </p>
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ResourcePageDTO", description = "ADMIN-课程视频信息分页")
public class ResourcePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "当前页")
    private int pageCurrent = 1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize = 20;
}
