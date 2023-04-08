package com.ninep.user.auth.controller;

import com.ninep.common.utils.Result;
import com.ninep.user.auth.response.AuthOrderPayResp;
import com.ninep.user.auth.vo.AuthOrderCancelVo;
import com.ninep.user.auth.vo.AuthOrderPayVo;
import com.ninep.user.service.IOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * AUTH-订单支付
 * @author NineP

 */
@Api(tags = "AUTH-订单支付")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth/order/pay")
public class AuthOrderPayController {
    @Resource
    private IOrderPayService orderPayService;

    @GetMapping("/test")
    public String test(){
        return "ok";
    }

    @ApiOperation(value = "支付接口", notes = "课程下单接口")
    @PostMapping(value = "create")
    public Result<AuthOrderPayResp> pay(@RequestBody AuthOrderPayVo payVo) {
        AuthOrderPayResp authOrderPayResp=orderPayService.pay(payVo);
        return Result.success(authOrderPayResp);
    }

    @ApiOperation(value = "取消支付", notes = "取消订单支付")
    @RequestMapping(value = "/cancel", method = RequestMethod.PUT)
    public Result<String> cancel(@RequestBody AuthOrderCancelVo authOrderCancelVo) {
        String result=orderPayService.cancel(authOrderCancelVo);
        return Result.success(result);
    }

}
