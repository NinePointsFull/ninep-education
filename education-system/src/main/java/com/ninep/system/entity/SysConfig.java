package com.ninep.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@TableName("sys_config")
@ApiModel(value = "SysConfig对象", description = "系统配置")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("配置类型(1:站点信息，2:系统信息、3:其他)")
    private Integer configType;

    @ApiModelProperty("内容类型(1:文本、2:富文本、3图片、4布尔、5枚举)")
    private Integer contentType;

    @ApiModelProperty("参数名称")
    private String configName;

    @ApiModelProperty("参数键名")
    private String configKey;

    @ApiModelProperty("参数键值")
    private String configValue;

    @ApiModelProperty("配置展示(0:隐藏、1:显示)")
    private Boolean configShow;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序，默认值:100")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Boolean getConfigShow() {
        return configShow;
    }

    public void setConfigShow(Boolean configShow) {
        this.configShow = configShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    @Override
    public String toString() {
        return "SysConfig{" +
            "id = " + id +
            ", configType = " + configType +
            ", contentType = " + contentType +
            ", configName = " + configName +
            ", configKey = " + configKey +
            ", configValue = " + configValue +
            ", configShow = " + configShow +
            ", remark = " + remark +
            ", sort = " + sort +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
        "}";
    }
}
