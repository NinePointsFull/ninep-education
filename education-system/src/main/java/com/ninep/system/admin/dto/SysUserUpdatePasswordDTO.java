package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 后台用户信息-更新密码
 *
 */
@Data
@Accessors(chain = true)
public class SysUserUpdatePasswordDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;
    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码", required = true)
    private String mobilePwd;
    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;

}
