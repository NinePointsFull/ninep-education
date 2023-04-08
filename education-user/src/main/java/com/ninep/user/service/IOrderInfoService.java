package com.ninep.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.common.utils.Page;
import com.ninep.user.admin.response.OrderInfoPageResp;
import com.ninep.user.admin.DTO.OrderInfoEditDTO;
import com.ninep.user.admin.DTO.OrderInfoPageDTO;
import com.ninep.user.auth.response.AuthOrderInfoResp;
import com.ninep.user.auth.vo.AuthOrderInfoVo;
import com.ninep.user.entity.OrderInfo;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IOrderInfoService extends IService<OrderInfo> {


    Page<AuthOrderInfoResp> listForPage(AuthOrderInfoVo infoVo);

    Page<OrderInfoPageResp> pageForOrder(OrderInfoPageDTO orderInfoPageDTO);

    String edit(OrderInfoEditDTO editVo);
}
