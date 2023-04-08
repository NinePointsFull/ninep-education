package com.ninep.system.service;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteNavPageDTO;
import com.ninep.system.admin.dto.SysWebsiteNavSaveDTO;
import com.ninep.system.admin.response.SysWebsiteNavPageResp;
import com.ninep.system.admin.dto.SysWebsiteNavEditDTO;
import com.ninep.system.api.response.ApiWebsiteNavResp;
import com.ninep.system.entity.WebsiteNav;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 头部导航 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface IWebsiteNavService extends IService<WebsiteNav> {

    Page<SysWebsiteNavPageResp> pageList(SysWebsiteNavPageDTO navPageVo);

    String saveNav(SysWebsiteNavSaveDTO saveVo);

    String editNav(SysWebsiteNavEditDTO editVo);

    String delete(Long id);

    Result<List<ApiWebsiteNavResp>> listForNav();
}
