package com.ninep.system.service;

import com.ninep.system.admin.response.SysMenuUserResp;
import com.ninep.system.admin.response.SysMenuViewResp;
import com.ninep.system.admin.dto.*;
import com.ninep.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.system.admin.response.SysMenuResp;

import java.util.List;

/**
 * <p>
 * 菜单信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuResp> list(SysMenuListDTO sysMenuListDTO);

    List<SysMenuUserResp> userList(SysMenuUserListDTO sysMenuUserListDTO);

    List<String> permissionList();

    String savMenu(SysMenuSaveDTO saveVo);

    String edit(SysMenuEditDTO editVo);

    SysMenuViewResp view(SysMenuViewDTO viewVo);

    String delete(SysMenuDeleteDTO deleteVo);
}
