package com.ninep.system.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.admin.response.SysMenuResp;
import com.ninep.system.admin.response.SysMenuUserResp;
import com.ninep.system.admin.response.SysMenuViewResp;
import com.ninep.system.admin.dto.*;
import com.ninep.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/system/admin/sys/menu")
public class SysMenuController {
    @Resource
    private ISysMenuService sysMenuService;


    @ApiOperation(value = "菜单列出", notes = "根据条件列出菜单")
    @PostMapping("/list")
    public Result<List<SysMenuResp>> list(@RequestBody SysMenuListDTO sysMenuListDTO){
        List<SysMenuResp> result=sysMenuService.list(sysMenuListDTO);
        return Result.success(result);
    }
    @ApiOperation(value = "用户菜单接口", notes = "获取指定用户的所有菜单和目录")
    @PostMapping(value = "/user/list")
    public Result<List<SysMenuUserResp>> userList(@RequestBody SysMenuUserListDTO sysMenuUserListDTO) {
        List<SysMenuUserResp> sysMenuUserRespList=sysMenuService.userList(sysMenuUserListDTO);
        return Result.success(sysMenuUserRespList);
    }

    @GetMapping("/permission/list")
    @ApiOperation(value = "用户权限接口", notes = "获取当前登录用户的权限")
    public Result<List<String>> permissionList(){
        List<String> list=sysMenuService.permissionList();
        return Result.success(list);
    }

    @ApiOperation(value = "菜单保存", notes = "菜单添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody SysMenuSaveDTO sysMenuSaveDTO) {
        return Result.success(sysMenuService.savMenu(sysMenuSaveDTO));
    }

    /**
     * 菜单信息更新接口
     */
    @ApiOperation(value = "菜单更新", notes = "菜单修改")
    @PostMapping(value = "/edit")
    public Result<String> edit(@RequestBody SysMenuEditDTO sysMenuEditDTO) {
        return Result.success(sysMenuService.edit(sysMenuEditDTO));
    }

    /**
     * 菜单信息查看接口
     */
    @ApiOperation(value = "菜单信息查看接口", notes = "菜单信息查看接口")
    @PostMapping(value = "/view")
    public Result<SysMenuViewResp> view(@RequestBody SysMenuViewDTO sysMenuViewDTO) {
        return Result.success(sysMenuService.view(sysMenuViewDTO));
    }


    /**
     * 菜单信息删除接口
     */
    @ApiOperation(value = "菜单删除", notes = "根据ID删除菜单")
    @PostMapping(value = "/delete")
    public Result<String> delete(@RequestBody SysMenuDeleteDTO sysMenuDeleteDTO) {
        return Result.success(sysMenuService.delete(sysMenuDeleteDTO));
    }
}
