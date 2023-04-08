package com.ninep.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户登录日志
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@TableName("log_login")
@ApiModel(value = "LogLogin对象", description = "用户登录日志")
public class LogLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("登录状态(2注册，1成功，0失败)")
    private Integer loginStatus;

    @ApiModelProperty("登录IP")
    private String loginIp;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("操作系统")
    private String os;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return "LogLogin{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", userId = " + userId +
            ", loginStatus = " + loginStatus +
            ", loginIp = " + loginIp +
            ", country = " + country +
            ", province = " + province +
            ", city = " + city +
            ", browser = " + browser +
            ", os = " + os +
        "}";
    }
}
