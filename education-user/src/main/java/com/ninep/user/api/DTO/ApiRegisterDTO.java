package com.ninep.user.api.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ApiRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机", required = true)
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message ="密码不能为空")
    private String mobilePwd;

    /**
     * 重复密码
     */
    @ApiModelProperty(value = "重复密码", required = true)
    @NotEmpty(message = "请确认密码")
    private String repassword;
    /**
     * 手机验证码
     */
    @ApiModelProperty(value = "手机验证码", required = true)
    @NotEmpty(message = "验证码不能为空")
    private String code;

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
