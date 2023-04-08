package com.ninep.system.api.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.json.JSONObject;
import com.ninep.common.utils.BaiJiaYunUtil;
import com.ninep.common.utils.EnumUtil;
import com.ninep.common.utils.Result;
import com.ninep.system.api.vo.ApiEnumVo;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 获取枚举信息
 *
 * @author liaoh
 */
@Slf4j
@RestController
@RequestMapping(value = "/system/api/common")
public class ApiCommonController {

    private static final String DEFAULT_ENUM_NAME = "name";
    private static final String DEFAULT_PREFIX = "com.ninep.common.enums.";


    @ApiOperation(value = "枚举通用接口", notes = "返回枚举信息")
    @PostMapping(value = "/enum")
    public Result<ArrayList> getEnumInfo(@RequestBody ApiEnumVo apiEnumVo)  {
        String className = new StringBuffer(DEFAULT_PREFIX).append(apiEnumVo.getEnumName()).toString();
        try {
            Class clazz = Class.forName(className);
            return Result.success(new ArrayList<>(EnumUtil.toList(clazz, DEFAULT_ENUM_NAME)));
        } catch (ClassNotFoundException e) {
            log.error("获取枚举失败, className={}", className, e);
            return Result.error("获取枚举失败");
        }
    }

}
