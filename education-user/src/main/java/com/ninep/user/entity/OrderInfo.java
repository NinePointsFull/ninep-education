package com.ninep.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@TableName("order_info")
@ApiModel(value = "OrderInfo对象", description = "订单信息表")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("下单用户编号")
    private Long userId;

    @ApiModelProperty("下单用户电话")
    private String mobile;

    @ApiModelProperty("下单用户注册时间")
    private LocalDateTime registerTime;

    @ApiModelProperty("课程ID")
    private Long courseId;

    @ApiModelProperty("划线价")
    private BigDecimal rulingPrice;

    @ApiModelProperty("课程价格")
    private BigDecimal coursePrice;

    @ApiModelProperty("支付方式：1微信支付，2支付宝支付")
    private Integer payType;

    @ApiModelProperty("订单状态：1待支付，2成功支付，3支付失败，4关闭支付")
    private Integer orderStatus;

    @ApiModelProperty("客户备注")
    private String remarkCus;

    @ApiModelProperty("后台备注")
    private String remark;

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

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", orderNo = " + orderNo +
            ", userId = " + userId +
            ", mobile = " + mobile +
            ", registerTime = " + registerTime +
            ", courseId = " + courseId +
            ", rulingPrice = " + rulingPrice +
            ", coursePrice = " + coursePrice +
            ", payType = " + payType +
            ", orderStatus = " + orderStatus +
            ", remarkCus = " + remarkCus +
            ", remark = " + remark +
            ", payTime = " + payTime +
        "}";
    }
}
