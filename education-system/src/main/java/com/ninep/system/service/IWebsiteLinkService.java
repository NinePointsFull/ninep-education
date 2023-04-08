package com.ninep.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.common.utils.Page;
import com.ninep.system.admin.dto.SysWebsiteLinkEditDTO;
import com.ninep.system.admin.dto.SysWebsiteLinkSaveDTO;
import com.ninep.system.admin.response.SysWebsiteLinkPageResp;
import com.ninep.system.admin.dto.SysWebsiteLinkPageDTO;
import com.ninep.system.api.response.ApiWebsiteLinkResp;
import com.ninep.system.entity.WebsiteLink;

import java.util.List;

/**
 * <p>
 * 站点友情链接 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface IWebsiteLinkService extends IService<WebsiteLink> {

    Page<SysWebsiteLinkPageResp> pageList(SysWebsiteLinkPageDTO pageVo);

    String saveLink(SysWebsiteLinkSaveDTO saveVo);

    String editLink(SysWebsiteLinkEditDTO editVo);

    String delete(Long id);

    List<ApiWebsiteLinkResp> listForLink();
}
