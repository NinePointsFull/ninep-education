package com.ninep.system.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysRolePageResp;
import com.ninep.system.admin.response.SysRoleViewResp;
import com.ninep.system.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 角色信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@RestController
@RequestMapping(value = "/system/admin/sys/role")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;
    /**
     * 角色分页列表接口
     */
    @ApiOperation(value = "角色分页", notes = "角色分页列表接口")
    @PostMapping(value = "/page")
    public Result<Page<SysRolePageResp>> listForPage(@RequestBody SysRolePageDTO pageVo) {
        Page<SysRolePageResp> page=sysRoleService.listForPage(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "角色查看接口", notes = "角色查看接口")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Result<SysRoleViewResp> view(@RequestBody SysRoleViewDTO sysRoleViewDTO) {
        return sysRoleService.view(sysRoleViewDTO);
    }

    /**
     * 角色更新接口
     */
    @ApiOperation(value = "角色更新接口", notes = "角色更新接口")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<String> update(@RequestBody @Validated SysRoleUpdateDTO sysRoleUpdateDTO) {
        return sysRoleService.update(sysRoleUpdateDTO);
    }

    /**
     * 角色添加接口
     */
    @ApiOperation(value = "角色保存", notes = "角色添加接口")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Validated SysRoleSaveDTO sysRoleSaveDTO) {
        return sysRoleService.save(sysRoleSaveDTO);
    }

    /**
     * 角色删除接口
     */
    @ApiOperation(value = "角色删除接口", notes = "角色删除接口")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result<String> delete(@RequestBody @Validated SysRoleDeleteDTO sysRoleDeleteDTO) {
        return sysRoleService.delete(sysRoleDeleteDTO);
    }
}
