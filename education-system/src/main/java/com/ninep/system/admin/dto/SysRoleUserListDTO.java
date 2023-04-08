package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色用户关联表-列出
 *
 */
@Data
@Accessors(chain = true)
public class SysRoleUserListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
