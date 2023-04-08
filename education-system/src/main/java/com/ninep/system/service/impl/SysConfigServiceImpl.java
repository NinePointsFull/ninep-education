package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.enums.*;
import com.ninep.common.utils.AssertUtil;
import com.ninep.system.admin.dto.SysConfigEditDTO;
import com.ninep.system.admin.dto.SysConfigListDTO;
import com.ninep.system.admin.response.SysConfigListResp;
import com.ninep.system.api.response.ApiSysConfigWebsiteResp;
import com.ninep.system.entity.SysConfig;
import com.ninep.system.mapper.SysConfigMapper;
import com.ninep.system.service.ISysConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    /**
     * 网站基本信息
     * //TODO 性能优化
     * @return
     */
    @Override
    @Cacheable(cacheNames = "system",key ="'website'" ,sync = true)
    public ApiSysConfigWebsiteResp websiteBase() {
        //获取所有配置
        List<SysConfig> sysConfigList = this.list();
        if (!sysConfigList.isEmpty()){
            //转换为map
            Map<String, String> sysMap = sysConfigList.stream().collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));
            ApiSysConfigWebsiteResp apiSysConfigWebsiteResp = BeanUtil.mapToBean(sysMap, ApiSysConfigWebsiteResp.class, true, CopyOptions.create());
            return apiSysConfigWebsiteResp;
        }
        return null;
    }

    @Override
    public UploadDTO upload() {
        List<SysConfig> list = this.list();
        AssertUtil.notEmpty(list,SysConfigEnum.CONFIG_ERROR);
        Map<String, String> map = list.stream().collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));
        UploadDTO uploadDTO = BeanUtil.mapToBean(map, UploadDTO.class,true, CopyOptions.create());
        return uploadDTO;
    }

    @Override
    public List<SysConfigListResp> listForCondition(SysConfigListDTO listVo) {
        AssertUtil.notNull(listVo, ResultEnum.OTHER_ERROR);
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysConfig::getSort).orderByDesc(SysConfig::getGmtModified);
        if (listVo.getConfigType()!=null){
            wrapper.eq(SysConfig::getConfigType,listVo.getConfigType());
        }
        //选择存储/视频/短信平台
        if (listVo.getConfigType().equals(3)){
            SysConfig vodPlatform = this.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, "vodPlatform"));
            AssertUtil.notNull(vodPlatform, ResultEnum.OTHER_ERROR);
            Integer configValue = Integer.valueOf(vodPlatform.getConfigValue());
            String tag = VodPlatformEnum.byCode(configValue).getTag();
            wrapper.like(SysConfig::getConfigKey,tag);
        }
        if (listVo.getConfigType().equals(4)){
            SysConfig storagePlatform = this.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, "storagePlatform"));
            AssertUtil.notNull(storagePlatform, ResultEnum.OTHER_ERROR);
            Integer configValue = Integer.valueOf(storagePlatform.getConfigValue());
            String tag = StoragePlatformEnum.byCode(configValue).getTag();
            wrapper.like(SysConfig::getConfigKey,tag);
        }
        if (listVo.getConfigType().equals(5)){
            SysConfig smsPlatform= this.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, "smsPlatform"));
            AssertUtil.notNull(smsPlatform, ResultEnum.OTHER_ERROR);
            Integer configValue = Integer.valueOf(smsPlatform.getConfigValue());
            String tag = SmsPlatformEnum.byCode(configValue).getTag();
            wrapper.like(SysConfig::getConfigKey,tag);
        }
        List<SysConfig> configs = this.list(wrapper);
        List<SysConfigListResp> list = BeanUtil.copyToList(configs, SysConfigListResp.class);

        return list;
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website'")
    public String edit(SysConfigEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        SysConfig sysConfig = BeanUtil.copyProperties(editVo, SysConfig.class);
        boolean b = this.updateById(sysConfig);
        if (b){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return  ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    public VodDTO vod() {
        //获取系统配置
        List<SysConfig> sysConfigs = this.list();
        AssertUtil.notNull(sysConfigs,SysConfigEnum.CONFIG_ERROR);
        Map<String, String> map = sysConfigs.stream().collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));
        VodDTO vodDTO = BeanUtil.mapToBean(map, VodDTO.class, true, CopyOptions.create());
        return vodDTO;
    }


}
