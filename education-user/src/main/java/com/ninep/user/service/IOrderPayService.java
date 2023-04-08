package com.ninep.user.service;

import com.ninep.user.auth.response.AuthOrderPayResp;
import com.ninep.user.auth.vo.AuthOrderCancelVo;
import com.ninep.user.auth.vo.AuthOrderPayVo;
import com.ninep.user.entity.OrderPay;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单支付信息表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IOrderPayService extends IService<OrderPay> {

    AuthOrderPayResp pay(AuthOrderPayVo payVo);

    String notify(HttpServletRequest request, Integer payModel, String payImpl);

    String cancel(AuthOrderCancelVo authOrderCancelVo);

    void updateOrderStatusByOrderNo(Long orderNo, Integer orderStatus);
}
