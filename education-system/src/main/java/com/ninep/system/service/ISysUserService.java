package com.ninep.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysLoginResp;
import com.ninep.system.admin.response.SysUserPageResp;
import com.ninep.system.admin.response.SysUserViewResp;
import com.ninep.system.entity.SysUser;

/**
 * <p>
 * 后台用户信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysUserService extends IService<SysUser> {

    SysLoginResp login(SysLoginDTO sysLoginDTO);


    Page<SysUserPageResp> listForPage(SysUserPageDTO sysUserPageDTO);

    Result<String> save(SysUserSaveDTO sysUserSaveDTO);

    Result<String> delete(SysUserDeleteDTO sysUserDeleteDTO);

    Result<String> update(SysUserUpdateDTO sysUserUpdateDTO);

    Result<SysUserViewResp> view(SysUserViewDTO req);

    Result<String> updatePassword(SysUserUpdatePasswordDTO sysUserUpdatePasswordDTO);
}
