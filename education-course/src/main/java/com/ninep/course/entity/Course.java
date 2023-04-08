package com.ninep.course.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 课程信息
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Course对象", description = "课程信息")
public class Course implements Serializable {

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

    @ApiModelProperty("讲师ID")
    private Long lecturerId;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课程封面")
    private String courseLogo;

    @ApiModelProperty("课程简介")
    private String introduce;

    @ApiModelProperty("是否免费(1:免费，0:收费)")
    private Integer isFree;

    @ApiModelProperty("划线价")
    private BigDecimal rulingPrice;

    @ApiModelProperty("课程价格")
    private BigDecimal coursePrice;

    @ApiModelProperty("是否上架(1:上架，0:下架)")
    private Integer isPutaway;

    @ApiModelProperty("课程排序(前端显示使用)")
    private Integer courseSort;

    @ApiModelProperty("购买人数")
    private Integer countBuy;

    @ApiModelProperty("学习人数")
    private Integer countStudy;

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

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLogo() {
        return courseLogo;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
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

    public Integer getIsPutaway() {
        return isPutaway;
    }

    public void setIsPutaway(Integer isPutaway) {
        this.isPutaway = isPutaway;
    }

    public Integer getCourseSort() {
        return courseSort;
    }

    public void setCourseSort(Integer courseSort) {
        this.courseSort = courseSort;
    }

    public Integer getCountBuy() {
        return countBuy;
    }

    public void setCountBuy(Integer countBuy) {
        this.countBuy = countBuy;
    }

    public Integer getCountStudy() {
        return countStudy;
    }

    public void setCountStudy(Integer countStudy) {
        this.countStudy = countStudy;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", lecturerId = " + lecturerId +
            ", categoryId = " + categoryId +
            ", courseName = " + courseName +
            ", courseLogo = " + courseLogo +
            ", introduce = " + introduce +
            ", isFree = " + isFree +
            ", rulingPrice = " + rulingPrice +
            ", coursePrice = " + coursePrice +
            ", isPutaway = " + isPutaway +
            ", courseSort = " + courseSort +
            ", countBuy = " + countBuy +
            ", countStudy = " + countStudy +
        "}";
    }
}
