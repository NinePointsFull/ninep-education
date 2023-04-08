package com.ninep.system.admin.controller;

import com.ninep.common.enums.ResultEnum;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysMenuRoleListDTO;
import com.ninep.system.admin.dto.SysMenuRoleSaveDTO;
import com.ninep.system.service.ISysMenuRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单角色关联表 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/system/admin/sys/menu/role")
public class SysMenuRoleController {

    @Autowired
    private ISysMenuRoleService sysMenuRoleService;

    /**
     * 列出菜单角色关联信息接口
     */
    @ApiOperation(value = "菜单ID列出", notes = "根据角色ID列出该角色的所有菜单ID")
    @PostMapping(value = "/list")
    public Result<List<Long>> list(@RequestBody @Validated SysMenuRoleListDTO sysMenuRoleListDTO) {
        List<Long> ids=sysMenuRoleService.list(sysMenuRoleListDTO);
        return Result.success(ids);
    }
    /**
     * 添加角色菜单关联信息接口
     */
    @ApiOperation(value = "角色菜单保存", notes = "角色菜单添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody SysMenuRoleSaveDTO sysMenuRoleSaveDTO) {
        sysMenuRoleService.save(sysMenuRoleSaveDTO);
        return Result.success(ResultEnum.SUCCESS.getMsg());
    }

}
