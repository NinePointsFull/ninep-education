package com.ninep.system.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.system.api.response.ApiWebsiteNavResp;
import com.ninep.system.service.IWebsiteNavService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 头部导航
 *
 * @author NineP
 */
@RestController
@RequestMapping(value = "/system/api/website/nav")
public class ApiWebsiteNavController {

   @Resource
   private IWebsiteNavService websiteNavService;

    /**
     * 头部导航接口
     *
     * @return 头部导航列表
     */
    @ApiOperation(value = "头部导航接口", notes = "返回头部导航列表")
    @GetMapping(value = "/list")
    public Result<List<ApiWebsiteNavResp>> list() {
        return websiteNavService.listForNav();
    }

}
