package com.ninep.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 后台操作日志表
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@TableName("sys_log")
@ApiModel(value = "SysLog对象", description = "后台操作日志表")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("操作人ID")
    private Long userId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("用户操作")
    private String operation;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("请求参数")
    private String content;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysLog{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", userId = " + userId +
            ", realName = " + realName +
            ", ip = " + ip +
            ", operation = " + operation +
            ", method = " + method +
            ", path = " + path +
            ", content = " + content +
        "}";
    }
}
