/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.ninep.user.api.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 用户基本信息
 * </p>
 */
@Data
@Accessors(chain = true)
public class ApiLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "登录密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 20,message = "密码要在6-20位之间")
    private String password;

    @ApiModelProperty(value = "登录IP", required = false)
    private String loginIp;
    @ApiModelProperty(value = "国家", required = false)
    private String country = "中国";
    @ApiModelProperty(value = "省", required = false)
    private String province;
    @ApiModelProperty(value = "市", required = false)
    private String city;
    @ApiModelProperty(value = "浏览器", required = false)
    private String browser;
    @ApiModelProperty(value = "操作系统", required = false)
    private String os;
}
