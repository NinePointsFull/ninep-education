package com.ninep.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.system.admin.dto.SysMenuRoleListDTO;
import com.ninep.system.admin.dto.SysMenuRoleSaveDTO;
import com.ninep.system.entity.SysMenuRole;
import com.ninep.system.mapper.SysMenuRoleMapper;
import com.ninep.system.service.ISysMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单角色关联表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class SysMenuRoleServiceImpl extends ServiceImpl<SysMenuRoleMapper, SysMenuRole> implements ISysMenuRoleService {


    /**
     * 获取当前role的菜单id
     * @param sysMenuRoleListDTO
     * @return
     */
    @Override
    public List<Long> list(SysMenuRoleListDTO sysMenuRoleListDTO) {
        List<SysMenuRole> menuRoles = this.list(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getRoleId, sysMenuRoleListDTO.getRoleId()));
        if (CollUtil.isNotEmpty(menuRoles)){
            return menuRoles.stream().map(SysMenuRole::getMenuId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuRoleSaveDTO sysMenuRoleSaveDTO) {
        //先删除，在添加
        if (CollUtil.isNotEmpty(sysMenuRoleSaveDTO.getMenuIdList())){
            this.remove(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getRoleId,sysMenuRoleSaveDTO.getRoleId()));
            List<SysMenuRole> sysMenuRoles = new ArrayList<>();
            for (Long menuId:sysMenuRoleSaveDTO.getMenuIdList()){
                SysMenuRole sysMenuRole = new SysMenuRole();
                sysMenuRole.setMenuId(menuId);
                sysMenuRole.setRoleId(sysMenuRoleSaveDTO.getRoleId());
                sysMenuRoles.add(sysMenuRole);
            }
            this.saveBatch(sysMenuRoles);
        }else {
            this.remove(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getRoleId,sysMenuRoleSaveDTO.getRoleId()));
        }
    }
}
