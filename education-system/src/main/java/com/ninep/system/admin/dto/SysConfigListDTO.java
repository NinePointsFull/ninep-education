package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ADMIN-系统配置
 * </p>
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysConfigListDTO", description = "ADMIN-系统配置添加")
public class SysConfigListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "配置类型(1:站点信息，2:系统信息、3:其他)")
    private Integer configType;

    @ApiModelProperty(value = "内容类型(1:文本、2:富文本、3图片、4布尔、5枚举)")
    private Integer contentType;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "配置展示(0:隐藏、1:显示)")
    private Boolean configShow;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序，默认值:100")
    private Integer sort;
}
