package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteCarouselEditDTO;
import com.ninep.system.admin.response.SysWebsiteCarouselPageResp;
import com.ninep.system.admin.dto.SysWebsiteCarouselPageDTO;
import com.ninep.system.admin.dto.SysWebsiteCarouselSaveDTO;
import com.ninep.system.api.response.ApiWebsiteCarouselResp;
import com.ninep.system.entity.WebsiteCarousel;
import com.ninep.system.mapper.WebsiteCarouselMapper;
import com.ninep.system.service.IWebsiteCarouselService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 广告信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class WebsiteCarouselServiceImpl extends ServiceImpl<WebsiteCarouselMapper, WebsiteCarousel> implements IWebsiteCarouselService {

    @Override
    public Page<SysWebsiteCarouselPageResp> pageForCondition(SysWebsiteCarouselPageDTO pageVo) {
        IPage<WebsiteCarousel> websiteCarouselIPage=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<WebsiteCarousel> order = new LambdaQueryWrapper<WebsiteCarousel>().orderByAsc(WebsiteCarousel::getSort).orderByDesc(WebsiteCarousel::getGmtModified);
        this.page(websiteCarouselIPage,order);
        Page<WebsiteCarousel> page = PageUtil.parse(websiteCarouselIPage);
        Page<SysWebsiteCarouselPageResp> transform = PageUtil.transform(page, SysWebsiteCarouselPageResp.class);
        return transform;
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'website_carousel'")
    public String edit(SysWebsiteCarouselEditDTO editVo) {
        WebsiteCarousel websiteCarousel = BeanUtil.copyProperties(editVo, WebsiteCarousel.class);
        boolean b = this.updateById(websiteCarousel);
        if (b){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'website_carousel'")
    public String deleteById(Long id) {
        boolean b = this.removeById(id);
        if (b){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    @CacheEvict(cacheNames = "course",key = "'website_carousel'")
    public String save(SysWebsiteCarouselSaveDTO saveVo) {
        AssertUtil.notNull(saveVo,ResultEnum.OTHER_ERROR.getCode(),ResultEnum.OTHER_ERROR.getMsg());
        WebsiteCarousel websiteCarousel = BeanUtil.copyProperties(saveVo, WebsiteCarousel.class);
        websiteCarousel.setBeginTime(LocalDateTime.now());
        websiteCarousel.setEndTime(LocalDateTime.now());
        boolean save = this.save(websiteCarousel);
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    @Cacheable(cacheNames = "system",key="'website_carousel'",sync = true)
    public Result<List<ApiWebsiteCarouselResp>> listForCarousel() {
        List<WebsiteCarousel> list = this.list(new LambdaQueryWrapper<WebsiteCarousel>().eq(WebsiteCarousel::getStatusId, StatusIdEnum.YES.getCode()));
        AssertUtil.notEmpty(list, SysConfigEnum.CONFIG_ERROR);
        List<ApiWebsiteCarouselResp> apiWebsiteCarouselResps = BeanUtil.copyToList(list, ApiWebsiteCarouselResp.class);
        return Result.success(apiWebsiteCarouselResps);
    }
}
