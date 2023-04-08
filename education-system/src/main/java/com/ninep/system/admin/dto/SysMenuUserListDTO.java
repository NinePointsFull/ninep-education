package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户菜单信息-列出
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class SysMenuUserListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID，为空则获取当前登录用户菜单", required = false)
    private Long userId;

}
