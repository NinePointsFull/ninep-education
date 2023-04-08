package com.ninep.system.service;

import com.ninep.system.admin.dto.SysMenuRoleListDTO;
import com.ninep.system.admin.dto.SysMenuRoleSaveDTO;
import com.ninep.system.entity.SysMenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单角色关联表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysMenuRoleService extends IService<SysMenuRole> {

    List<Long> list(SysMenuRoleListDTO sysMenuRoleListDTO);

    void save(SysMenuRoleSaveDTO sysMenuRoleSaveDTO);
}
