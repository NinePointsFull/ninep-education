package com.ninep.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.kisso.enums.TokenOrigin;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.cache.CacheRedis;
import com.ninep.common.enums.LoginStatusEnum;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.enums.UserLoginEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.*;
import com.ninep.user.admin.DTO.UsersEditDTO;
import com.ninep.user.admin.DTO.UsersPageDTO;
import com.ninep.user.admin.response.UsersPageResp;
import com.ninep.user.api.DTO.ApiLoginDTO;
import com.ninep.user.api.DTO.ApiRegisterDTO;
import com.ninep.user.api.DTO.ApiSendCodeDTO;
import com.ninep.user.api.resonse.ApiUsersLoginResp;
import com.ninep.user.auth.response.AuthUsersResp;
import com.ninep.user.entity.LogLogin;
import com.ninep.user.entity.Users;
import com.ninep.user.mapper.LogLoginMapper;
import com.ninep.user.mapper.UsersMapper;
import com.ninep.user.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Resource
    private LogLoginMapper logLoginMapper;
    @Autowired
    private CacheRedis cacheRedis;

    @Override
    public Page<UsersPageResp> pageList(UsersPageDTO pageVo) {
        IPage<Users> userPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(), pageVo.getPageSize());
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Users::getGmtModified);
        if (StringUtils.hasText(pageVo.getMobile())) {
            wrapper.like(Users::getMobile, pageVo.getMobile());
        }
        this.page(userPage, wrapper);
        Page<Users> parse = PageUtil.parse(userPage);
        Page<UsersPageResp> transform = PageUtil.transform(parse, UsersPageResp.class);
        if (!CollectionUtils.isEmpty(transform.getList())) {
            for (UsersPageResp usersPageResp : transform.getList()) {
                usersPageResp.setMobile(DesensitizedUtil.mobilePhone(usersPageResp.getMobile()));
            }
        }
        return transform;
    }

    @Override
    public String edit(UsersEditDTO editVo) {
        AssertUtil.notNull(editVo, ResultEnum.OTHER_ERROR);
        AssertUtil.notNull(editVo.getId(), ResultEnum.OTHER_ERROR);
        Users users = BeanUtil.copyProperties(editVo, Users.class);
        return resultUser(users);
    }

    @Override
    public ApiUsersLoginResp login(ApiLoginDTO loginVo) {
        //根据手机号查询用户
        Users user = this.getOne(new LambdaQueryWrapper<Users>().eq(Users::getMobile, loginVo.getMobile()));
        AssertUtil.notNull(user, UserLoginEnum.USER_NOT_EXIT.getCode(), UserLoginEnum.USER_NOT_EXIT.getMsg());
        //比对密码
        String mobileSalt = user.getMobileSalt();
        String mobilePsw = user.getMobilePsw();
        String hashpw = BCrypt.hashpw(loginVo.getPassword(), mobileSalt);
        AssertUtil.isTrue(mobilePsw.equals(hashpw), UserLoginEnum.USER_ERROR.getCode(), UserLoginEnum.USER_ERROR.getMsg());
        //记入登入日志
        LogLogin logLogin = BeanUtil.copyProperties(loginVo, LogLogin.class);
        logLogin.setUserId(user.getId());
        logLoginMapper.insert(logLogin);
        ApiUsersLoginResp apiUsersLoginResp = new ApiUsersLoginResp();
        apiUsersLoginResp.setMobile(user.getMobile());
        apiUsersLoginResp.setToken(SSOToken.create().setIssuer("NineP").setId(user.getId()).setOrigin(TokenOrigin.HTML5).getToken());
        // token，放入缓存
        cacheRedis.set(apiUsersLoginResp.getToken(), user.getId(), 1, TimeUnit.DAYS);
        return apiUsersLoginResp;
    }

    @Override
    public AuthUsersResp view() {
        //根据id查询用户
        Users user = this.getById(ThreadContextUtil.userId());
        if (user!=null && StatusIdEnum.YES.getCode().equals(user.getStatusId())){
            return BeanUtil.copyProperties(user, AuthUsersResp.class);
        }
        throw new BaseException("用户不存在或已被禁用");
    }

    @Override
    public String sendCode(ApiSendCodeDTO apiSendCodeDTO) {
        String code = NOUtil.getVerCode();
        log.info("验证码{}"+code);
        //todo发送短信
        /*if (SmsUtil.sendVerCode(req.getMobile(), code, feignSysConfig.getSms())) {
            // 缓存5分钟
            cacheRedis.set(Constants.RedisPre.CODE + req.getMobile(), code, 5, TimeUnit.MINUTES);
            return Result.success("发送成功");
        }*/
        cacheRedis.set(ConstantsUtil.RedisPre.CODE+apiSendCodeDTO.getMobile(),code,5,TimeUnit.MINUTES);

        return ResultEnum.SUCCESS.getMsg();
    }

    @Override
    public ApiUsersLoginResp register(ApiRegisterDTO apiRegisterDTO) {

        // 验证码校验
        String redisCode = cacheRedis.get(ConstantsUtil.RedisPre.CODE + apiRegisterDTO.getMobile());
        if (!StringUtils.hasText(redisCode)) {
            throw new BaseException("验证码已过期");
        }
        if (!apiRegisterDTO.getCode().equals(redisCode)) {
            throw new BaseException("验证码不正确");
        }
        // 密码校验
        if (!apiRegisterDTO.getMobilePwd().equals(apiRegisterDTO.getRepassword())) {
            throw new BaseException("两次密码不一致");
        }

        // 手机号重复校验
        Users user = this.getByMobile(apiRegisterDTO.getMobile());
        if (null != user) {
            throw new BaseException("该手机号已经注册，请更换手机号");
        }

        // 用户注册
        user = register2(apiRegisterDTO.getMobile(), apiRegisterDTO.getMobilePwd());

        // 日志
        loginLog(user.getId(), LoginStatusEnum.REGISTER, apiRegisterDTO);

        ApiUsersLoginResp apiUsersLoginResp = new ApiUsersLoginResp();
        apiUsersLoginResp.setMobile(user.getMobile());
        apiUsersLoginResp.setToken(SSOToken.create().setId(String.valueOf(user.getId())).setIssuer("ninep").setOrigin(TokenOrigin.HTML5).getToken());
        return apiUsersLoginResp;
    }

    /**
     * 注册日志
     * @param id
     * @param register
     * @param apiRegisterDTO
     */
    private void loginLog(Long id, LoginStatusEnum register, ApiRegisterDTO apiRegisterDTO) {
        LogLogin record = BeanUtil.copyProperties(apiRegisterDTO, LogLogin.class);
        record.setUserId(id);
        record.setLoginStatus(register.getCode());
        logLoginMapper.insert(record);
    }

    private Users register2(String mobile, String mobilePwd) {
        // 用户基本信息
        Users user = new Users();
        user.setMobile(mobile);
        user.setMobileSalt(BCrypt.gensalt());
        user.setMobilePsw(BCrypt.hashpw(mobilePwd,user.getMobileSalt()));
        this.save(user);
        return user;
    }

    /**
     * 手机号重复校验
     *
     * @param mobile
     * @return
     */
    private Users getByMobile(String mobile) {
        return this.getOne(new LambdaQueryWrapper<Users>().eq(Users::getMobile,mobile));
    }

    private String resultUser(Users users) {
        boolean b = this.updateById(users);
        if (b) {
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }
}
