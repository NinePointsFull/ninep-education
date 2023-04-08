package com.ninep.user.service.impl;

import com.ninep.user.entity.Msg;
import com.ninep.user.mapper.MsgMapper;
import com.ninep.user.service.IMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站内信息表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}
