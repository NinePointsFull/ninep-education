package com.ninep.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单支付信息表
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@TableName("order_pay")
@ApiModel(value = "OrderPay对象", description = "订单支付信息表")
public class OrderPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("流水号")
    private Long serialNumber;

    @ApiModelProperty("划线价")
    private BigDecimal rulingPrice;

    @ApiModelProperty("课程价格")
    private BigDecimal coursePrice;

    @ApiModelProperty("支付方式：1微信支付，2支付宝支付，3积分支付，4手工录单")
    private Integer payType;

    @ApiModelProperty("订单状态：1待支付，2成功支付，3支付失败，4已关闭，5已退款, 6订单解绑")
    private Integer orderStatus;

    @ApiModelProperty("客户备注")
    private String remarkCus;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getRulingPrice() {
        return rulingPrice;
    }

    public void setRulingPrice(BigDecimal rulingPrice) {
        this.rulingPrice = rulingPrice;
    }

    public BigDecimal getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(BigDecimal coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemarkCus() {
        return remarkCus;
    }

    public void setRemarkCus(String remarkCus) {
        this.remarkCus = remarkCus;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "OrderPay{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", orderNo = " + orderNo +
            ", serialNumber = " + serialNumber +
            ", rulingPrice = " + rulingPrice +
            ", coursePrice = " + coursePrice +
            ", payType = " + payType +
            ", orderStatus = " + orderStatus +
            ", remarkCus = " + remarkCus +
            ", payTime = " + payTime +
        "}";
    }
}
