package com.ninep.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysRoleUserListDTO;
import com.ninep.system.admin.dto.SysRoleUserSaveDTO;
import com.ninep.system.entity.SysRoleUser;
import com.ninep.system.mapper.SysRoleMapper;
import com.ninep.system.mapper.SysRoleUserMapper;
import com.ninep.system.service.ISysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色用户关联表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements ISysRoleUserService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Result<List<Long>> list(SysRoleUserListDTO sysRoleUserListDTO) {

        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId,sysRoleUserListDTO.getUserId());
        List<SysRoleUser> list = baseMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<Long> roleIdList = list.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
            return Result.success(roleIdList);
        }
        return Result.success(new ArrayList<>());


    }

    @Override
    public Result<String> save(SysRoleUserSaveDTO sysRoleUserSaveDTO) {
        if (sysRoleUserSaveDTO.getUserId() == null) {
            return Result.error("用户ID不能为空");
        }
        // 先删除旧的角色
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId,sysRoleUserSaveDTO.getUserId());
        baseMapper.delete(wrapper);
        // 再新增
        if (CollectionUtil.isNotEmpty(sysRoleUserSaveDTO.getRoleIdList())) {
            for (Long roleId : sysRoleUserSaveDTO.getRoleIdList()) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(sysRoleUserSaveDTO.getUserId());
                baseMapper.insert(sysRoleUser);
            }
        }
        return Result.success("操作成功");
    }
}
