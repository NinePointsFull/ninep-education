package com.ninep.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.StatusIdEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.PageUtil;
import com.ninep.common.utils.Result;
import com.ninep.system.admin.dto.SysWebsiteNavSaveDTO;
import com.ninep.system.admin.response.SysWebsiteNavPageResp;
import com.ninep.system.admin.dto.SysWebsiteNavEditDTO;
import com.ninep.system.admin.dto.SysWebsiteNavPageDTO;
import com.ninep.system.api.response.ApiWebsiteNavResp;
import com.ninep.system.entity.WebsiteNav;
import com.ninep.system.mapper.WebsiteNavMapper;
import com.ninep.system.service.IWebsiteNavService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 头部导航 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@Service
public class WebsiteNavServiceImpl extends ServiceImpl<WebsiteNavMapper, WebsiteNav> implements IWebsiteNavService {

    @Override
    public Page<SysWebsiteNavPageResp> pageList(SysWebsiteNavPageDTO navPageVo) {
        String navTitle = navPageVo.getNavTitle();
        IPage<WebsiteNav> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(navPageVo.getPageCurrent(),navPageVo.getPageSize());
        LambdaQueryWrapper<WebsiteNav> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(WebsiteNav::getSort).orderByDesc(WebsiteNav::getGmtModified);
        if (StringUtils.hasText(navTitle)){
            wrapper.like(WebsiteNav::getNavTitle,navTitle);
        }
        this.page(page,wrapper);
        Page<WebsiteNav> parse = PageUtil.parse(page);
        Page<SysWebsiteNavPageResp> transform = PageUtil.transform(parse, SysWebsiteNavPageResp.class);
        return transform;
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_nav'")
    public String saveNav(SysWebsiteNavSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, ResultEnum.OTHER_ERROR);
        WebsiteNav websiteNav = BeanUtil.copyProperties(saveVo, WebsiteNav.class);
        boolean save = this.save(websiteNav);
        return navResult(save);
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_nav'")
    public String editNav(SysWebsiteNavEditDTO editVo) {
        AssertUtil.notNull(editVo,ResultEnum.OTHER_ERROR);
        WebsiteNav websiteNav = BeanUtil.copyProperties(editVo, WebsiteNav.class);
        boolean b = this.updateById(websiteNav);
        return navResult(b);
    }

    private static String navResult(boolean b) {
        if (b){
            return ResultEnum.OTHER_SUCCESS.getMsg();
        }
        return ResultEnum.OTHER_ERROR.getMsg();
    }

    @Override
    @CacheEvict(cacheNames = "system",key = "'website_nav'")
    public String delete(Long id) {
        boolean b = this.removeById(id);
        return navResult(b);
    }

    @Override
    @Cacheable(cacheNames = "system",key = "'website_nav'",sync = true)
    public Result<List<ApiWebsiteNavResp>> listForNav() {
        List<WebsiteNav> list = this.list(new LambdaQueryWrapper<WebsiteNav>().eq(WebsiteNav::getStatusId, StatusIdEnum.YES.getCode()));
        AssertUtil.notNull(list, SysConfigEnum.CONFIG_ERROR);
        List<ApiWebsiteNavResp> apiWebsiteNavResps = BeanUtil.copyToList(list, ApiWebsiteNavResp.class);
        return Result.success(apiWebsiteNavResps);
    }
}
