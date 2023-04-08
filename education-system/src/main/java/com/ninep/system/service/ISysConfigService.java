package com.ninep.system.service;

import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.DTO.VodDTO;
import com.ninep.system.admin.response.SysConfigListResp;
import com.ninep.system.admin.dto.SysConfigEditDTO;
import com.ninep.system.admin.dto.SysConfigListDTO;
import com.ninep.system.api.response.ApiSysConfigWebsiteResp;
import com.ninep.system.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface ISysConfigService extends IService<SysConfig> {

    ApiSysConfigWebsiteResp websiteBase();


    UploadDTO upload();

    List<SysConfigListResp> listForCondition(SysConfigListDTO listVo);

    String edit(SysConfigEditDTO editVo);

    VodDTO vod();
}
