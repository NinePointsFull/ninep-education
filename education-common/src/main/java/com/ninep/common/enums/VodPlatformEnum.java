package com.ninep.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点播平台枚举
 *
 * @author LYQ
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VodPlatformEnum {

    /**
     * 私有云
     */
    PRIVATE(1, "私有云(待开发)", "pri%"),

    /**
     * 保利威
     */
    POLYV(2, "保利威", "polyv%"),

    /**
     * 百家云
     */
    BJYUN(3, "百家云(待实现)", "baijy%"),

    /**
     * 腾讯云
     */
    TENCENT(4,"腾讯云vod(待实现)","tencent%"),
    /**
     * 阿里云
     */
    ALIYUN(5,"阿里云vod(待实现)","aliyun%");
    /**
     * 编码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 标记
     */
    private final String tag;

    /**
     * 根据编码获取点播平台枚举
     *
     * @param code 编码
     * @return 点播平台枚举
     */
    public static VodPlatformEnum byCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (VodPlatformEnum value : VodPlatformEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
