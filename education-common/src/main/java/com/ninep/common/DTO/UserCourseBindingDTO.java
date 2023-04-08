package com.ninep.common.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 课程用户关联表
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class UserCourseBindingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 购买类型(1支付，2免费)
     */
    private Integer buyType;

}
