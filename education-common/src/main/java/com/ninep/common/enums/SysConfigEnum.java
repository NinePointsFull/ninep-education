package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author NineP
 */

@Getter
@AllArgsConstructor
public enum SysConfigEnum {
    CONFIG_ERROR(400,"配置异常"),

    ALIYUN_SP(1,"阿里云"),
    MinIO_SP(2,"MinIO"),
    LOCAL_SP(3,"本地");;
    private Integer code;
    private String msg;

}
