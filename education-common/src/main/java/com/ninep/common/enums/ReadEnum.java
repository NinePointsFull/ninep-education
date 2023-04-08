/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wujing
 */
@Getter
@AllArgsConstructor
public enum ReadEnum {

    READ(1, "已读"), NO(0, "未读");

    private Integer code;

    private String desc;

}
