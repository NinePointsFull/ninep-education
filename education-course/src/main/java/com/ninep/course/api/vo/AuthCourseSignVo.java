package com.ninep.course.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * API-AUTH-课程信息
 * </p>
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthCourseSignVo", description = "API-AUTH-课程信息")
public class AuthCourseSignVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID，若课程ID存在，则获取该课程的最新学习课时")
    private Long courseId;

    @ApiModelProperty(value = "课时ID")
    private Long periodId;

    @ApiModelProperty(value = "观看者IP")
    private String clientIp;

}
