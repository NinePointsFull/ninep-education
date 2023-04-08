package com.ninep.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 站内信用户记录表
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@TableName("msg_user")
@ApiModel(value = "MsgUser对象", description = "站内信用户记录表")
public class MsgUser implements Serializable {

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

    @ApiModelProperty("短信ID")
    private Long msgId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信类型(1系统消息,2其他)")
    private Byte msgType;

    @ApiModelProperty("短信标题")
    private String msgTitle;

    @ApiModelProperty("是否阅读(1是;0否)")
    private Byte isRead;

    @ApiModelProperty("是否置顶(1是;0否)")
    private Byte isTop;

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

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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

    public Byte getIsRead() {
        return isRead;
    }

    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        return "MsgUser{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", msgId = " + msgId +
            ", userId = " + userId +
            ", mobile = " + mobile +
            ", msgType = " + msgType +
            ", msgTitle = " + msgTitle +
            ", isRead = " + isRead +
            ", isTop = " + isTop +
        "}";
    }
}
