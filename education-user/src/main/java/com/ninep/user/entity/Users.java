package com.ninep.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Users对象", description = "用户信息")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态(1:正常，0:禁用)")
    private Integer statusId;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("密码盐")
    private String mobileSalt;

    @ApiModelProperty("登录密码")
    private String mobilePsw;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户性别(1男，2女，3保密)")
    private Integer userSex;

    @ApiModelProperty("用户年龄")
    private Integer userAge;

    @ApiModelProperty("用户头像")
    private String userHead;

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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileSalt() {
        return mobileSalt;
    }

    public void setMobileSalt(String mobileSalt) {
        this.mobileSalt = mobileSalt;
    }

    public String getMobilePsw() {
        return mobilePsw;
    }

    public void setMobilePsw(String mobilePsw) {
        this.mobilePsw = mobilePsw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Users{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", mobile = " + mobile +
            ", mobileSalt = " + mobileSalt +
            ", mobilePsw = " + mobilePsw +
            ", nickname = " + nickname +
            ", userSex = " + userSex +
            ", userAge = " + userAge +
            ", userHead = " + userHead +
            ", remark = " + remark +
        "}";
    }
}
