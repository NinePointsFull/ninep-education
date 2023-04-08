package com.ninep.system.service;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysRolePageResp;
import com.ninep.system.admin.response.SysRoleViewResp;
import com.ninep.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysRoleService extends IService<SysRole> {

    Page<SysRolePageResp> listForPage(SysRolePageDTO pageVo);

    Result<SysRoleViewResp> view(SysRoleViewDTO sysRoleViewDTO);

    Result<String> update(SysRoleUpdateDTO sysRoleUpdateDTO);

    Result<String> save(SysRoleSaveDTO sysRoleSaveDTO);

    Result<String> delete(SysRoleDeleteDTO sysRoleDeleteDTO);
}
