package com.ninep.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 课程用户学习日志
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@TableName("user_study")
@ApiModel(value = "UserStudy对象", description = "课程用户学习日志")
public class UserStudy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("课程ID")
    private Long courseId;

    @ApiModelProperty("章节ID")
    private Long chapterId;

    @ApiModelProperty("课时ID")
    private Long periodId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("进度(百分比)")
    private BigDecimal progress;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "UserStudy{" +
            "id = " + id +
            ", gmtCreate = " + gmtCreate +
            ", gmtModified = " + gmtModified +
            ", courseId = " + courseId +
            ", chapterId = " + chapterId +
            ", periodId = " + periodId +
            ", userId = " + userId +
            ", progress = " + progress +
        "}";
    }
}
