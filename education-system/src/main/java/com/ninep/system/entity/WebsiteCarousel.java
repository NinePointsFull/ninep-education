package com.ninep.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 广告信息
 * </p>
 *
 * @author NineP
 * @since 2022-11-26
 */
@TableName("website_carousel")
@ApiModel(value = "WebsiteCarousel对象", description = "广告信息")
public class WebsiteCarousel implements Serializable {

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

    @ApiModelProperty("广告标题")
    private String carouselTitle;

    @ApiModelProperty("广告图片")
    private String carouselImg;

    @ApiModelProperty("广告链接")
    private String carouselUrl;

    @ApiModelProperty("广告跳转方式")
    private String carouselTarget;

    @ApiModelProperty("开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

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

    public String getCarouselTitle() {
        return carouselTitle;
    }

    public void setCarouselTitle(String carouselTitle) {
        this.carouselTitle = carouselTitle;
    }

    public String getCarouselImg() {
        return carouselImg;
    }

    public void setCarouselImg(String carouselImg) {
        this.carouselImg = carouselImg;
    }

    public String getCarouselUrl() {
        return carouselUrl;
    }

    public void setCarouselUrl(String carouselUrl) {
        this.carouselUrl = carouselUrl;
    }

    public String getCarouselTarget() {
        return carouselTarget;
    }

    public void setCarouselTarget(String carouselTarget) {
        this.carouselTarget = carouselTarget;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "WebsiteCarousel{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", carouselTitle = " + carouselTitle +
            ", carouselImg = " + carouselImg +
            ", carouselUrl = " + carouselUrl +
            ", carouselTarget = " + carouselTarget +
            ", beginTime = " + beginTime +
            ", endTime = " + endTime +
        "}";
    }
}
