package com.ninep.system.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.api.response.ApiWebsiteLinkResp;
import com.ninep.system.service.IWebsiteLinkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情链接
 *
 * @author NineP
 */
@RestController
@RequestMapping(value = "/system/api/website/link")
public class ApiWebsiteLinkController {

    @Resource
    private IWebsiteLinkService websiteLinkService;
    /**
     * 友情链接接口
     *
     * @return 返回友情链接列表
     */
    @ApiOperation(value = "友情链接接口", notes = "返回友情链接列表")
    @GetMapping(value = "/list")
    public Result<List<ApiWebsiteLinkResp>> list() {
        List<ApiWebsiteLinkResp> result=websiteLinkService.listForLink();
        return Result.success(result);
    }

}
