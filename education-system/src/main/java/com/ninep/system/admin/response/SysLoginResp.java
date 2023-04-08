package com.ninep.system.admin.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLoginResp", description = "用户登录")
public class SysLoginResp implements Serializable {

    private static final long serialVersionUID = -5227677558610916215L;

    @ApiModelProperty(value = "登录账号", required = true)
    private String mobile;

    @ApiModelProperty(value = "昵称", required = true)
    private String realName;

    @ApiModelProperty(value = "token，有效期为1天", required = true)
    private String token;
}
