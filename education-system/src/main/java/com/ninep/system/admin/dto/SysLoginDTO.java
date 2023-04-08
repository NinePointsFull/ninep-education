package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUsersLoginVo", description = "用户登录")
public class SysLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号", required = true)
    private String mobile;

    @NotBlank(message = "登录密码不能为空")
    //@Pattern(regexp = "/^1[3456789]\\d{9}$/",message = "手机号不正确")
    @ApiModelProperty(value = "登录密码", required = true)
    private String mobilePwd;

}
