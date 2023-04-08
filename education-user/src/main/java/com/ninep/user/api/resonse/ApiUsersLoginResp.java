/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.ninep.user.api.resonse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class ApiUsersLoginResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * token，设置有效期为1天
     */
    @ApiModelProperty(value = "token，有效期为1天")
    private String token;
}
