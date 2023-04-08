package com.ninep.user.service;

import com.ninep.common.utils.Page;
import com.ninep.user.admin.DTO.UsersPageDTO;
import com.ninep.user.api.DTO.ApiRegisterDTO;
import com.ninep.user.api.DTO.ApiSendCodeDTO;
import com.ninep.user.api.resonse.ApiUsersLoginResp;
import com.ninep.user.api.DTO.ApiLoginDTO;
import com.ninep.user.auth.response.AuthUsersResp;
import com.ninep.user.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.user.admin.response.UsersPageResp;
import com.ninep.user.admin.DTO.UsersEditDTO;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IUsersService extends IService<Users> {

    Page<UsersPageResp> pageList(UsersPageDTO pageVo);

    String edit(UsersEditDTO editVo);

    ApiUsersLoginResp login(ApiLoginDTO loginVo);

    AuthUsersResp view();

    String sendCode(ApiSendCodeDTO apiSendCodeDTO);

    ApiUsersLoginResp register(ApiRegisterDTO apiRegisterDTO);
}
