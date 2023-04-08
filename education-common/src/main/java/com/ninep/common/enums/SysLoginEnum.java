package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author NineP
 */

@AllArgsConstructor
@Getter
public enum SysLoginEnum {
    USER_NOT_EXIST(201,"用户不存在"),USER_STATUS_DISABLE(202,"该账号被禁用"),USER_PASSWORD_ERROR(203,"密码错误"),
    USER_ID_NOT_EMPTY(204,"主键ID不能为空");
    private Integer code;
    private String msg;
}
