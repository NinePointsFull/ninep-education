package com.ninep.user.admin.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.user.admin.response.UsersPageResp;
import com.ninep.user.service.IUsersService;
import com.ninep.user.admin.DTO.UsersEditDTO;
import com.ninep.user.admin.DTO.UsersPageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-用户信息")
@RestController
@RequestMapping("/user/admin/users")
public class UsersController {

    @Resource
    private IUsersService usersService;

    @ApiOperation(value = "用户信息分页", notes = "用户信息分页")
    @PostMapping(value = "/page")
    public Result<Page<UsersPageResp>> page(@RequestBody UsersPageDTO pageVo) {
        Page<UsersPageResp> page=usersService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "用户信息修改", notes = "用户信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody UsersEditDTO editVo) {
        return Result.success(usersService.edit(editVo));
    }

}
