package com.ninep.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 站点导航文章
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class ApiEnumVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @ApiModelProperty(value = "枚举名称")
    private String enumName;
}
