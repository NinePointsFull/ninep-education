package com.ninep.system.service;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteCarouselPageDTO;
import com.ninep.system.admin.response.SysWebsiteCarouselPageResp;
import com.ninep.system.admin.dto.SysWebsiteCarouselEditDTO;
import com.ninep.system.admin.dto.SysWebsiteCarouselSaveDTO;
import com.ninep.system.api.response.ApiWebsiteCarouselResp;
import com.ninep.system.entity.WebsiteCarousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 广告信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
public interface IWebsiteCarouselService extends IService<WebsiteCarousel> {

    Page<SysWebsiteCarouselPageResp> pageForCondition(SysWebsiteCarouselPageDTO pageVo);

    String edit(SysWebsiteCarouselEditDTO editVo);

    String deleteById(Long id);

    String save(SysWebsiteCarouselSaveDTO saveVo);

    Result<List<ApiWebsiteCarouselResp>> listForCarousel();
}
