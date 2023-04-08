package com.ninep.user.auth.controller;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.user.auth.response.AuthOrderInfoResp;
import com.ninep.user.auth.vo.AuthOrderInfoVo;
import com.ninep.user.service.IOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * AUTH-订单信息表
 *
 * @author NineP
 */
@Api(tags = "AUTH-订单信息表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth/order/info")
public class AuthOrderInfoController {

    @Resource
    private IOrderInfoService orderInfoService;

    @ApiOperation(value = "分页接口", notes = "根据条件分页列出订单信息")
    @PostMapping(value = "/page")
    public Result<Page<AuthOrderInfoResp>> listForPage(@RequestBody AuthOrderInfoVo infoVo) {
        Page<AuthOrderInfoResp> orderInfoRespPage=orderInfoService.listForPage(infoVo);
        return Result.success(orderInfoRespPage);
    }


}
