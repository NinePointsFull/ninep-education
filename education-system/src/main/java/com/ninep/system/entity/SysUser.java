package com.ninep.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 后台用户信息
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "后台用户信息")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;

    @ApiModelProperty("状态(1:正常，0:禁用)")
    private Integer statusId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("密码盐")
    private String mobileSalt;

    @ApiModelProperty("登录密码")
    private String mobilePsw;

    @ApiModelProperty("真实姓名")
    private String realName;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysUser{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", mobile = " + mobile +
            ", mobileSalt = " + mobileSalt +
            ", mobilePsw = " + mobilePsw +
            ", realName = " + realName +
            ", remark = " + remark +
        "}";
    }
}
