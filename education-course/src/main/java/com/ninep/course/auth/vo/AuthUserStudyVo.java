package com.ninep.course.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * API-AUTH-课程用户学习日志
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthUserStudyVo", description = "API-AUTH-课程用户学习日志")
public class AuthUserStudyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学习ID")
    private Long studyId;

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

    @ApiModelProperty(value = "当前观看时长")
    private BigDecimal currentDuration;

    @ApiModelProperty(value = "总时长")
    private BigDecimal totalDuration;

}
