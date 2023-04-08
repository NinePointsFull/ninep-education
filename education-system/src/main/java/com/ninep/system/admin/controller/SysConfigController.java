package com.ninep.system.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysConfigEditDTO;
import com.ninep.system.admin.dto.SysConfigListDTO;
import com.ninep.system.admin.response.SysConfigListResp;
import com.ninep.system.entity.SysConfig;
import com.ninep.system.service.ISysConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统配置 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Slf4j
@RestController
@RequestMapping("/system/admin/sys/config")
public class SysConfigController {

    @Resource
    private ISysConfigService sysConfigService;

    @GetMapping("/upload")
    public Result<UploadDTO> upload(){
        return Result.success(sysConfigService.upload());
    }

    @ApiOperation(value = "系统配置列表", notes = "系统配置列表")
    @PostMapping(value = "/list")
    public Result<List<SysConfigListResp>> list(@RequestBody SysConfigListDTO listVo) {
        List<SysConfigListResp> list=sysConfigService.listForCondition(listVo);
        return Result.success(list);
    }

    @ApiOperation(value = "系统配置修改", notes = "系统配置修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody SysConfigEditDTO sysConfigEditDTO) {
        return Result.success(sysConfigService.edit(sysConfigEditDTO));
    }

    @GetMapping("/vod/config")
    public VodDTO vod(){
        return sysConfigService.vod();
    }

    @GetMapping("/getByConfigType")
    public Map<String ,String > getByConfigType(@RequestParam("code") Integer code){
        List<SysConfig> sysConfigs = sysConfigService.list(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigType, code));
        if (!CollUtil.isEmpty(sysConfigs)){
            Map<String, String> result = sysConfigs.stream().collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));
            return result;
        }
        return new HashMap<String,String>();
    }

    @GetMapping("/getwWbsiteDomainByKey")
    public String getwWbsiteDomainByKey(@RequestParam("key") String key){
        SysConfig one = sysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, key));
        if (one!=null){
            return one.getConfigValue();
        }
        return "";
    }
}
