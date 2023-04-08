package com.ninep.user.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * API-AUTH-订单支付信息表
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthOrderCancelVo", description = "API-AUTH-订单取消支付")
public class AuthOrderCancelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号", required = true)
    private Long orderNo;
}
