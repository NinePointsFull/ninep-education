package com.ninep.system.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单角色关联表-添加
 * @author NineP

 */
@Data
@Accessors(chain = true)
public class SysMenuRoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;
    /**
     * 菜单ID集合
     */
    private List<Long> menuIdList;
}
