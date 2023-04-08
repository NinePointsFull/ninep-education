package com.ninep.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 站内信息表
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Msg对象", description = "站内信息表")
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态(1有效, 0无效)")
    private Byte statusId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("短信类型(1系统消息,2其他)")
    private Byte msgType;

    @ApiModelProperty("短信标题")
    private String msgTitle;

    @ApiModelProperty("短信内容")
    private String msgText;

    @ApiModelProperty("是否定时发送（1是，0否）")
    private Byte isTimeSend;

    @ApiModelProperty("发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("是否已发送(1是;0否)")
    private Byte isSend;

    @ApiModelProperty("是否置顶(1是;0否)")
    private Byte isTop;

    @ApiModelProperty("备注")
    private String remark;

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

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Byte getIsTimeSend() {
        return isTimeSend;
    }

    public void setIsTimeSend(Byte isTimeSend) {
        this.isTimeSend = isTimeSend;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public Byte getIsSend() {
        return isSend;
    }

    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Msg{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", msgType = " + msgType +
            ", msgTitle = " + msgTitle +
            ", msgText = " + msgText +
            ", isTimeSend = " + isTimeSend +
            ", sendTime = " + sendTime +
            ", isSend = " + isSend +
            ", isTop = " + isTop +
            ", remark = " + remark +
        "}";
    }
}
