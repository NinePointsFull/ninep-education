package com.ninep.user.api.controller;

import com.ninep.user.service.IOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * API-订单支付信息表
 *
 * @author NineP
 */
@Api(tags = "API-订单支付信息表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/order/pay")
public class ApiOrderPayController {

   @Resource
   private IOrderPayService orderPayService;

    @ApiOperation(value = "支付通知", notes = "支付回调通知")
    @RequestMapping(value = "/notify/{payModel}/{payImpl}")
    public String notify(HttpServletRequest request, @PathVariable Integer payModel, @PathVariable String payImpl) {
        return orderPayService.notify(request, payModel, payImpl);
    }

}
