package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {
    SUCCESS(200,"成功"),
    ERROR(999,"错误"),
    TOKEN_PAST(301, "token过期"),
    TOKEN_ERROR(302, "token异常"),
    MENU_PAST(305, "菜单过期"),
    MENU_NO(306, "没此权限，请联系管理员！"),
    OTHER_SUCCESS(2000,"操作成功"),
    OTHER_ERROR(9999,"操作失败")
    ;
    private Integer code;
    private String msg;
}
