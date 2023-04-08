package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-站点友情链接
 * </p>
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysWebsiteLinkEditDTO", description = "ADMIN-站点友情链接修改")
public class SysWebsiteLinkEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态(1有效, 0无效)")
    private Integer statusId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "名称")
    private String linkName;

    @ApiModelProperty(value = "链接")
    private String linkUrl;

    @ApiModelProperty(value = "跳转方式(_blank，_self)")
    private String linkTarget;
}