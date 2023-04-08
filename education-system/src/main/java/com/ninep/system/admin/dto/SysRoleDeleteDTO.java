package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色信息-删除
 *
 * @author wujing
 */
@Data
@Accessors(chain = true)
public class SysRoleDeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @NotNull
    private Long id;

}
