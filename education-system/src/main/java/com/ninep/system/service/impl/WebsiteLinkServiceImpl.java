package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.system.admin.dto.SysWebsiteLinkSaveDTO;
import com.ninep.system.admin.response.SysWebsiteLinkPageResp;
import com.ninep.system.admin.dto.SysWebsiteLinkEditDTO;
import com.ninep.system.admin.dto.SysWebsiteLinkPageDTO;
import com.ninep.system.api.response.ApiWebsiteLinkResp;
import com.ninep.system.entity.WebsiteLink;
import com.ninep.system.mapper.WebsiteLinkMapper;
import com.ninep.system.service.IWebsiteLinkService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 站点友情链接 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class WebsiteLinkServiceImpl extends ServiceImpl<WebsiteLinkMapper, WebsiteLink> implements IWebsiteLinkService {

    @Override
    public Page<SysWebsiteLinkPageResp> pageList(SysWebsiteLinkPageDTO pageVo) {
        IPage<WebsiteLink> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageVo.getPageCurrent(),pageVo.getPageSize());
        LambdaQueryWrapper<WebsiteLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(WebsiteLink::getSort).orderByDesc(WebsiteLink::getGmtModified);
        if (StringUtils.hasText(pageVo.getLinkName())){
            wrapper.like(WebsiteLink::getLinkName,pageVo.getLinkName());
        }
        this.page(page,wrapper);
        Page<WebsiteLink> parse = PageUtil.parse(page);
        Page<SysWebsiteLinkPageResp> transform = PageUtil.transform(parse, SysWebsiteLinkPageResp.class);
        return transform;
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_link'")
    public String saveLink(SysWebsiteLinkSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, ResultEnum.OTHER_ERROR);
        WebsiteLink websiteLink = BeanUtil.copyProperties(saveVo, WebsiteLink.class);
        boolean save = this.save(websiteLink);
        return getResult(save);
    }

    private static String getResult(boolean save) {
        if (save){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_link'")
    public String editLink(SysWebsiteLinkEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        WebsiteLink websiteLink = BeanUtil.copyProperties(editVo, WebsiteLink.class);
        boolean b = this.updateById(websiteLink);
        return getResult(b);
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_link'")
    public String delete(Long id) {
        boolean b = this.removeById(id);
        return getResult(b);
    }

    @Override
    @Cacheable(cacheNames = "system",key = "'website_link'",sync = true)
    public List<ApiWebsiteLinkResp> listForLink() {
        List<WebsiteLink> list = this.list(new LambdaQueryWrapper<WebsiteLink>().eq(WebsiteLink::getStatusId, StatusIdEnum.YES.getCode()));
        if (CollUtil.isEmpty(list)){
            return null;
        }
        List<ApiWebsiteLinkResp> apiWebsiteLinkResps = BeanUtil.copyToList(list, ApiWebsiteLinkResp.class);
        return apiWebsiteLinkResps;
    }
}
