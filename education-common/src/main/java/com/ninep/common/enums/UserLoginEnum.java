package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author NineP
 */

@AllArgsConstructor
@Getter
public enum UserLoginEnum {
    USER_NOT_EXIT(501,"该用户不存在"),
    USER_ERROR(502,"用户名或密码错误")
    ;


    private Integer code;
    private String msg;
}
