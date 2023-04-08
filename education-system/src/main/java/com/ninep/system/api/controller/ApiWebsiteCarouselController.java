package com.ninep.system.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.api.response.ApiWebsiteCarouselResp;
import com.ninep.system.service.IWebsiteCarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * API-广告信息
 *
 * @author NineP
 */
@Api(tags = "API-广告信息")
@RestController
@RequestMapping("/system/api/website/carousel")
public class ApiWebsiteCarouselController {

    @Resource
    private IWebsiteCarouselService websiteCarouselService;


    @ApiOperation(value = "广告列表接口", notes = "首页轮播广告列出")
    @GetMapping(value = "/list")
    public Result<List<ApiWebsiteCarouselResp>> list() {
        return websiteCarouselService.listForCarousel();
    }

}
