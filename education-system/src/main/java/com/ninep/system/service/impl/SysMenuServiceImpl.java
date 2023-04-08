package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.common.enums.MenuTypeEnum;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.ThreadContextUtil;
import com.ninep.system.admin.response.SysMenuUserResp;
import com.ninep.system.admin.response.SysMenuViewResp;
import com.ninep.system.admin.dto.*;
import com.ninep.system.entity.SysMenu;
import com.ninep.system.entity.SysMenuRole;
import com.ninep.system.entity.SysRoleUser;
import com.ninep.system.mapper.SysMenuMapper;
import com.ninep.system.admin.response.SysMenuResp;
import com.ninep.system.mapper.SysMenuRoleMapper;
import com.ninep.system.mapper.SysRoleUserMapper;
import com.ninep.system.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public List<SysMenuResp> list(SysMenuListDTO sysMenuListDTO) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (sysMenuListDTO !=null && StringUtils.hasText(sysMenuListDTO.getMenuName())){
            wrapper.eq(SysMenu::getMenuName, sysMenuListDTO.getMenuName());
        }
        if (sysMenuListDTO !=null && sysMenuListDTO.getStatusId()!=null){
            wrapper.eq(SysMenu::getStatusId, sysMenuListDTO.getStatusId());
        }
        List<SysMenu> sysMenus = this.list(wrapper);
        return filter(0L,sysMenus);
    }

    @Override
    public List<SysMenuUserResp> userList(SysMenuUserListDTO sysMenuUserListDTO) {
        if (ObjectUtil.isEmpty(sysMenuUserListDTO) || ObjectUtil.isEmpty(sysMenuUserListDTO.getUserId())) {
            log.info("当前线程：{}",Thread.currentThread().getId());
            sysMenuUserListDTO.setUserId(ThreadContextUtil.userId());
        }
        //当前用户的所有权限
        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectList(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, sysMenuUserListDTO.getUserId()));
        if (CollectionUtil.isEmpty(sysRoleUserList)) {
            return new ArrayList<>();
        }
        List<Long> roleList = sysRoleUserList.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
        //当前权限下符合条件的menu
        List<SysMenuRole> menuRoleList = sysMenuRoleMapper.selectList(new LambdaQueryWrapper<SysMenuRole>().in(SysMenuRole::getRoleId,roleList));
        if (CollectionUtil.isEmpty(menuRoleList)) {
            return new ArrayList<>();
        }
        // 只要菜单和目录
        List<Long> menuList = menuRoleList.stream().map(SysMenuRole::getMenuId).collect(Collectors.toList());
        //selevt * from sysmenu where id in (list) and menutype in(1,2) and status =1
        List<SysMenu> sysMenuList =this.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId,menuList).
                in(SysMenu::getMenuType,MenuTypeEnum.MENU.getCode(),MenuTypeEnum.DIRECTORY.getCode()).
                eq(SysMenu::getStatusId, StatusIdEnum.YES.getCode()));
        return filters(0L, sysMenuList);
    }

    @Override
    public List<String> permissionList() {
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, ThreadContextUtil.userId()));
        if (CollectionUtil.isEmpty(sysRoleUsers)) {
            return new ArrayList<>();
        }
        // 用户的所有角色
        List<Long> roleList = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());

        List<SysMenuRole> menuRoleList =sysMenuRoleMapper.selectList(new LambdaQueryWrapper<SysMenuRole>().in(SysMenuRole::getRoleId,roleList));
        if (CollectionUtil.isEmpty(menuRoleList)) {
            return new ArrayList<>();
        }
        // 用户的所有菜单
        List<Long> menuList = menuRoleList.stream().map(SysMenuRole::getMenuId).collect(Collectors.toList());
        List<SysMenu> sysMenuList=this.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId,menuList).eq(SysMenu::getMenuType,MenuTypeEnum.PERMISSION.getCode()).eq(SysMenu::getStatusId,StatusIdEnum.YES.getCode()));

        return sysMenuList.stream().map(SysMenu::getAuthValue).collect(Collectors.toList());
    }

    @Override
    public String savMenu(SysMenuSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, ResultEnum.OTHER_ERROR);
        SysMenu sysMenu = BeanUtil.copyProperties(saveVo, SysMenu.class);
        boolean save = this.save(sysMenu);
        return menuResult(save);
    }

    private static String menuResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    public String edit(SysMenuEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        SysMenu sysMenu = BeanUtil.copyProperties(editVo, SysMenu.class);
        boolean b = this.updateById(sysMenu);
        return menuResult(b);
    }

    @Override
    public SysMenuViewResp view(SysMenuViewDTO viewVo) {
        AssertUtil.notNull(viewVo,ResultEnum.OTHER_ERROR);
        SysMenu sysMenu = this.getById(viewVo.getId());
        return BeanUtil.copyProperties(sysMenu, SysMenuViewResp.class);
    }

    @Override
    public String delete(SysMenuDeleteDTO deleteVo) {
        AssertUtil.notNull(deleteVo,ResultEnum.OTHER_ERROR);
        //获取子类
        List<SysMenu> child = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, deleteVo.getId()));
        if (child!=null && !child.isEmpty()){
            return "请先删除子菜单";
        }
        boolean b = this.removeById(deleteVo.getId());
        return menuResult(b);
    }

    /**
     * 封装菜单
     * @param id
     * @param sysMenus
     * @return
     */
    private List<SysMenuResp> filter(Long id, List<SysMenu> sysMenus) {
        List<SysMenu> collect = sysMenus.stream().filter(item -> id.equals(item.getParentId())).collect(Collectors.toList());
        if (!collect.isEmpty()){
            //如果存在子类
            List<SysMenuResp> sysMenuResps = BeanUtil.copyToList(collect, SysMenuResp.class);
            for (SysMenuResp sysMenuResp:sysMenuResps){
                sysMenuResp.setChildrenList(filter(sysMenuResp.getId(),sysMenus));
            }
            return sysMenuResps;
        }
        return null;
    }
    /**
     * 菜单层级处理
     */
    private List<SysMenuUserResp> filters(Long parentId, List<SysMenu> menuList) {
        List<SysMenu> sysMenuList = menuList.stream().filter(item -> parentId.compareTo(item.getParentId()) == 0).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(sysMenuList)) {
            List<SysMenuUserResp> respList = copys(sysMenuList);
            for (SysMenuUserResp resp : respList) {
                resp.setChildren(filters(resp.getId(), menuList));
            }
            return respList;
        }
        return null;
    }
    private List<SysMenuUserResp> copys(List<SysMenu> sysMenuList) {
        List<SysMenuUserResp> respList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            SysMenuUserResp resp = new SysMenuUserResp();
            resp.setId(sysMenu.getId());
            resp.setName(sysMenu.getMenuName());
            resp.setNameEn(sysMenu.getMenuName());
            resp.setMenuType(sysMenu.getMenuType());
            resp.setPath(sysMenu.getMenuUrl());
            resp.setSort(sysMenu.getSort());
            resp.setTargetName(sysMenu.getMenuIcon());
            respList.add(resp);
        }
        return respList;
    }


}
