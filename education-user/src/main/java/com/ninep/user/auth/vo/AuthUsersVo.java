package com.ninep.user.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * API-AUTH-用户信息
 * </p>
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthUsersVo", description = "API-AUTH-用户信息")
public class AuthUsersVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户性别(1男，2女，3保密)")
    private Integer userSex;

    @ApiModelProperty(value = "用户年龄")
    private Integer userAge;

    @ApiModelProperty(value = "用户头像")
    private String userHead;

    @ApiModelProperty(value = "备注")
    private String remark;
}
