package com.ninep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传方式
 *
 * @author LHR
 */
@Getter
@AllArgsConstructor
public enum StoragePlatformEnum {

    ALIYUN(1, "阿里云", "aliyun%", "aliyun"),
    MinIO(2, "MinIO", "minio%", "minio"),
    LOCAL(3, "本地", "local%", "local"),
    TENCENT(4,"腾讯云","tencent%","tencent");

    private Integer code;

    private String desc;

    /**
     * 标记
     */
    private String tag;

    /**
     * 标记
     */
    private String mode;

    /**
     * 根据编码获取枚举信息
     *
     * @param code 编码
     * @return 枚举信息
     */
    public static StoragePlatformEnum byCode(Integer code) {
        for (StoragePlatformEnum val : StoragePlatformEnum.values()) {
            if (val.getCode().equals(code)) {
                return val;
            }
        }
        return null;
    }
}
