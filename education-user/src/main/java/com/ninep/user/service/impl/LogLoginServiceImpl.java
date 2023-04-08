package com.ninep.user.service.impl;

import com.ninep.user.entity.LogLogin;
import com.ninep.user.mapper.LogLoginMapper;
import com.ninep.user.service.ILogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {

}
