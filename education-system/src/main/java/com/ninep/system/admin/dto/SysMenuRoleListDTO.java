package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单角色关联表-列出
 */
@Data
@Accessors(chain = true)
public class SysMenuRoleListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;
}
