package com.ninep.course.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * API-课程信息
 * </p>
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CourseVo", description = "API-课程信息")
public class CourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private Long courseId;

}
