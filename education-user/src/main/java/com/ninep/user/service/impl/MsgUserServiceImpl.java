package com.ninep.user.service.impl;

import com.ninep.user.entity.MsgUser;
import com.ninep.user.mapper.MsgUserMapper;
import com.ninep.user.service.IMsgUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站内信用户记录表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class MsgUserServiceImpl extends ServiceImpl<MsgUserMapper, MsgUser> implements IMsgUserService {

}
