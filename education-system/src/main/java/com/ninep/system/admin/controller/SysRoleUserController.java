package com.ninep.system.admin.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysRoleUserListDTO;
import com.ninep.system.admin.dto.SysRoleUserSaveDTO;
import com.ninep.system.service.ISysRoleUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色用户关联表 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/system/admin/sys/role/user")
public class SysRoleUserController {

    @Autowired
    private ISysRoleUserService sysRoleUserService;

    /**
     * 列出角色用户关联信息接口
     */
    @ApiOperation(value = "角色用户列出", notes = "根据用户ID列出该用户的所有角色")
    @PostMapping(value = "/list")
    public Result<List<Long>> list(@RequestBody @Validated SysRoleUserListDTO sysRoleUserListDTO) {
        return sysRoleUserService.list(sysRoleUserListDTO);
    }

    /**
     * 添加用户角色信息接口
     */
    @ApiOperation(value = "角色用户保存", notes = "用户角色添加接口")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody SysRoleUserSaveDTO sysRoleUserSaveDTO) {
        return sysRoleUserService.save(sysRoleUserSaveDTO);
    }

}
