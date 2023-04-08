package com.ninep.course.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * API-AUTH-课程用户关联表
 * </p>
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthUserCourseVo", description = "API-AUTH-课程用户关联表")
public class AuthUserCourseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer pageCurrent = 1;
    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize = 20;
}
