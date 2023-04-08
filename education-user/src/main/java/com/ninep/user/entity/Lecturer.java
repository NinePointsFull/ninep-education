package com.ninep.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 讲师信息
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@ApiModel(value = "Lecturer对象", description = "讲师信息")
public class Lecturer implements Serializable {

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

    @ApiModelProperty("讲师名称")
    private String lecturerName;

    @ApiModelProperty("讲师手机")
    private String lecturerMobile;

    @ApiModelProperty("讲师职位")
    private String lecturerPosition;

    @ApiModelProperty("讲师头像")
    private String lecturerHead;

    @ApiModelProperty("简介")
    private String introduce;

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

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerMobile() {
        return lecturerMobile;
    }

    public void setLecturerMobile(String lecturerMobile) {
        this.lecturerMobile = lecturerMobile;
    }

    public String getLecturerPosition() {
        return lecturerPosition;
    }

    public void setLecturerPosition(String lecturerPosition) {
        this.lecturerPosition = lecturerPosition;
    }

    public String getLecturerHead() {
        return lecturerHead;
    }

    public void setLecturerHead(String lecturerHead) {
        this.lecturerHead = lecturerHead;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", statusId = " + statusId +
            ", sort = " + sort +
            ", lecturerName = " + lecturerName +
            ", lecturerMobile = " + lecturerMobile +
            ", lecturerPosition = " + lecturerPosition +
            ", lecturerHead = " + lecturerHead +
            ", introduce = " + introduce +
        "}";
    }
}
