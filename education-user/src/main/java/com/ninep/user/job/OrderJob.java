package com.ninep.user.job;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.common.enums.OrderStatusEnum;
import com.ninep.user.entity.OrderInfo;
import com.ninep.user.service.IOrderInfoService;
import com.ninep.user.service.IOrderPayService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderJob {

    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private IOrderPayService orderPayService;

    /**
     * 每60秒执行一次
     */
    @XxlJob("orderJobHandler")
    public void order() {
        // 处理过期订单
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOrderStatus,OrderStatusEnum.WAIT.getCode());
        //待支付订单有效期30分钟
        wrapper.lt(OrderInfo::getGmtCreate,LocalDateTime.now().minusMinutes(30));
        List<OrderInfo> orderInfoList = orderInfoService.list(wrapper);
        if (CollUtil.isNotEmpty(orderInfoList)) {
            for (OrderInfo orderInfo : orderInfoList) {
                orderInfo.setOrderStatus(OrderStatusEnum.CLOSE.getCode());
                orderInfoService.updateById(orderInfo);
                orderPayService.updateOrderStatusByOrderNo(orderInfo.getOrderNo(), orderInfo.getOrderStatus());
            }
        }
        log.info("处理过期订单=》》》》");
        XxlJobHelper.handleSuccess("完成");
    }

}
