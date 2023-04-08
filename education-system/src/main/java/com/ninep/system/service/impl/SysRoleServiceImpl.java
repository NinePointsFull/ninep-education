package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysRolePageResp;
import com.ninep.system.admin.response.SysRoleViewResp;
import com.ninep.system.entity.SysMenuRole;
import com.ninep.system.entity.SysRole;
import com.ninep.system.entity.SysRoleUser;
import com.ninep.system.mapper.SysMenuRoleMapper;
import com.ninep.system.mapper.SysRoleMapper;
import com.ninep.system.mapper.SysRoleUserMapper;
import com.ninep.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public Page<SysRolePageResp> listForPage(SysRolePageDTO pageVo) {
        IPage<SysRole> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysRole::getSort).orderByDesc(SysRole::getGmtModified);
        if (StringUtils.hasText(pageVo.getRoleName())){
            wrapper.like(SysRole::getRoleName,pageVo.getRoleName());
        }
        if (pageVo.getStatusId()!=null){
            wrapper.eq(SysRole::getStatusId,pageVo.getStatusId());
        }
        this.page(page,wrapper);
        Page<SysRole> parse = PageUtil.parse(page);
        Page<SysRolePageResp> transform = PageUtil.transform(parse, SysRolePageResp.class);
        return transform;
    }

    @Override
    public Result<SysRoleViewResp> view(SysRoleViewDTO sysRoleViewDTO) {
        if (sysRoleViewDTO.getId() == null) {
            return Result.error("角色ID不能为空");
        }
        SysRole record = this.getById(sysRoleViewDTO.getId());
        if (ObjectUtil.isNull(record)) {
            return Result.error("找不到角色信息");
        }
        return Result.success(BeanUtil.copyProperties(record, SysRoleViewResp.class));
    }

    @Override
    public Result<String> update(SysRoleUpdateDTO sysRoleUpdateDTO) {
        SysRole record = BeanUtil.copyProperties(sysRoleUpdateDTO, SysRole.class);
        boolean results = this.updateById(record);
        if (results) {
            return Result.success("操作成功");
        }
        return Result.error(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    public Result<String> save(SysRoleSaveDTO sysRoleSaveDTO) {
        SysRole record = BeanUtil.copyProperties(sysRoleSaveDTO, SysRole.class);
        int results = baseMapper.insert(record);
        if (results > 0) {
            return Result.success("操作成功");
        }
        return Result.error(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> delete(SysRoleDeleteDTO sysRoleDeleteDTO) {
        // 1、删除关联表
        LambdaQueryWrapper<SysMenuRole> menuRoleWrapper = new LambdaQueryWrapper<>();
        menuRoleWrapper.eq(SysMenuRole::getRoleId,sysRoleDeleteDTO.getId());
        sysMenuRoleMapper.delete(menuRoleWrapper);
        LambdaQueryWrapper<SysRoleUser> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysRoleUser::getRoleId,sysRoleDeleteDTO.getId());
        sysRoleUserMapper.delete(userRoleWrapper);
        // 2、删除角色
        int results = baseMapper.deleteById(sysRoleDeleteDTO.getId());
        if (results > 0) {
            return Result.success("操作成功");
        }
        return Result.error(ResultEnum.OTHER_ERROR.getMsg());
    }
}
