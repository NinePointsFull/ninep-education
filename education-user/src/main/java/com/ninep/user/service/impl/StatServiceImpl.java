package com.ninep.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.ninep.user.admin.response.StatLogin;
import com.ninep.user.api.resonse.StatLoginResp;
import com.ninep.user.mapper.LogLoginMapper;
import com.ninep.user.service.StatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private LogLoginMapper logLoginMapper;

    @Override
    public StatLoginResp statLogin(Integer dates) {
        StatLoginResp statLoginResp = new StatLoginResp();
        List<StatLogin> statLogins=logLoginMapper.statByDate(DateUtil.offsetDay(new Date(),dates));
        if (CollUtil.isNotEmpty(statLogins)) {
            statLoginResp.setDateList(statLogins.stream().map(StatLogin::getDates).distinct().collect(Collectors.toList()));
            Map<String, Long> loginMap = statLogins.stream().filter(s -> s.getLoginStatus().equals(1)).collect(Collectors.toMap(s -> s.getDates(), s -> s.getLogins()));
            Map<String, Long> registerMap = statLogins.stream().filter(s -> s.getLoginStatus().equals(2)).collect(Collectors.toMap(s -> s.getDates(), s -> s.getLogins()));
            List<Long> loginList = new ArrayList<>();
            List<Long> registerList = new ArrayList<>();
            for (String data : statLoginResp.getDateList()) {
                loginList.add(loginMap.get(data) == null ? 0 : loginMap.get(data));
                registerList.add(registerMap.get(data) == null ? 0 : registerMap.get(data));
            }
            statLoginResp.setLoginList(loginList);
            statLoginResp.setRegisterList(registerList);
        }
        return statLoginResp;
    }
}
