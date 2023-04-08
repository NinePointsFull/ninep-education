package com.ninep.course.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 课程视频信息
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Resource对象", description = "课程视频信息")
public class Resource implements Serializable {

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

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源类型(1:视频2:音频;3:文档)")
    private Integer resourceType;

    @ApiModelProperty("资源大小")
    private Long resourceSize;

    @ApiModelProperty("资源地址")
    private String resourceUrl;

    @ApiModelProperty("状态(1转码中，2成功，3失败)")
    private Integer videoStatus;

    @ApiModelProperty("时长")
    private String videoLength;

    @ApiModelProperty("vid")
    private String videoVid;

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

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceSize() {
        return resourceSize;
    }

    public void setResourceSize(Long resourceSize) {
        this.resourceSize = resourceSize;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(Integer videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getVideoVid() {
        return videoVid;
    }

    public void setVideoVid(String videoVid) {
        this.videoVid = videoVid;
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", resourceName = " + resourceName +
            ", resourceType = " + resourceType +
            ", resourceSize = " + resourceSize +
            ", resourceUrl = " + resourceUrl +
            ", videoStatus = " + videoStatus +
            ", videoLength = " + videoLength +
            ", videoVid = " + videoVid +
        "}";
    }
}
