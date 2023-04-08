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
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LecturerPageDTO", description = "ADMIN-讲师信息分页")
public class LecturerPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师名称")
    private String lecturerName;

    @ApiModelProperty(value = "当前页")
    private int pageCurrent = 1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize = 20;
}
