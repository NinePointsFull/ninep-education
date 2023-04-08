package com.ninep.system.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 角色信息-保存
 *
 */
@Data
@Accessors(chain = true)
public class SysRoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty
    private String roleName;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = false)
    private String remark;
}
