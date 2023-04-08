package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SysMenuListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单名称", required = false)
    private String menuName;

    @ApiModelProperty(value = "状态", required = false)
    private Integer statusId;

}
