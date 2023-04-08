package com.ninep.system.service.impl;

import com.ninep.system.entity.SysLog;
import com.ninep.system.mapper.SysLogMapper;
import com.ninep.system.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台操作日志表 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}
