package com.ninep.system.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.common.utils.ThreadContextUtil;
import com.ninep.system.admin.dto.*;
import com.ninep.system.admin.response.SysUserViewResp;
import com.ninep.system.admin.response.SysUserPageResp;
import com.ninep.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@RestController
@Slf4j
@RequestMapping("/system/admin/sys/user")
public class SysUserController {
    @Resource
    private ISysUserService userService;

    @ApiOperation(value = "当前登录用户", notes = "获取当前登录用户")
    @GetMapping(value = "/current")
    public Result<SysUserViewResp> currentView() {
        SysUserViewDTO sysUserViewDTO = new SysUserViewDTO();
        sysUserViewDTO.setId(ThreadContextUtil.userId());
        return userService.view(sysUserViewDTO);
    }


    @ApiOperation(value = "后台管理员分页列表接口", notes = "后台管理员分页列表接口")
    @PostMapping(value = "/page")
    public Result<Page<SysUserPageResp>> listForPage(@RequestBody SysUserPageDTO sysUserPageDTO) {
        Page<SysUserPageResp> page= userService.listForPage(sysUserPageDTO);
        return Result.success(page);
    }

    /**
     * 后台管理员添加接口
     */
    @ApiOperation(value = "后台管理员添加接口", notes = "后台管理员添加接口")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<String> save(@RequestBody SysUserSaveDTO sysUserSaveDTO) {
        return userService.save(sysUserSaveDTO);
    }

    /**
     * 后台管理员删除接口
     */
    @ApiOperation(value = "后台管理员删除接口", notes = "后台管理员删除接口")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result<String> delete(@RequestBody SysUserDeleteDTO sysUserDeleteDTO) {
        return userService.delete(sysUserDeleteDTO);
    }

    /**
     * 后台管理员更新接口
     */
    @ApiOperation(value = "后台管理员更新接口", notes = "后台管理员更新接口")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<String> update(@RequestBody SysUserUpdateDTO sysUserUpdateDTO) {
        return userService.update(sysUserUpdateDTO);
    }

    /**
     * 后台管理员查看接口
     */
    @ApiOperation(value = "后台管理员查看接口", notes = "后台管理员查看接口")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Result<SysUserViewResp> view(@RequestBody SysUserViewDTO sysUserViewDTO) {
        return userService.view(sysUserViewDTO);
    }

    /**
     * 后台管理员密码接口
     */
    @ApiOperation(value = "后台管理员密码接口", notes = "后台管理员更新密码接口")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Result<String> updatePassword(@RequestBody SysUserUpdatePasswordDTO sysUserUpdatePasswordDTO) {
        return userService.updatePassword(sysUserUpdatePasswordDTO);
    }

}
