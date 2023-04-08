package com.ninep.system.service;

import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysRoleUserListDTO;
import com.ninep.system.admin.dto.SysRoleUserSaveDTO;
import com.ninep.system.entity.SysRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色用户关联表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysRoleUserService extends IService<SysRoleUser> {

    Result<List<Long>> list(SysRoleUserListDTO sysRoleUserListDTO);

    Result<String> save(SysRoleUserSaveDTO sysRoleUserSaveDTO);
}
