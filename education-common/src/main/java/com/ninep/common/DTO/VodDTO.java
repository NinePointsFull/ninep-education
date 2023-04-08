package com.ninep.common.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 点播配置
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class VodDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网关域名
     */
    private String websiteDomain;

    /**
     * 点播平台(1:私有云、2:保利威、3:百家云 4.腾讯云 5.阿里云)
     */
    private Integer vodPlatform;

    /**
     * 保利威--账号ID
     */
    private String polyvUserId;

    /**
     * 保利威--写入令牌
     */
    private String polyvWriteToken;

    /**
     * 保利威--读取秘钥
     */
    private String polyvReadToken;

    /**
     * 保利威--安全秘钥
     */
    private String polyvSecretKey;

    /**
     * 保利威--应用ID
     */
    private String polyvAppId;

    /**
     * 保利威--应用秘钥
     */
    private String polyvAppSecret;


    private String baijyPartnerId;
    private String baijyPartnerKey;
    private String baijySecretKey;
    private String baijyDomain;
    private String tencentkey;
    private String tencentSecret;
    private String aliyunkey;
    private String aliyunSecret;

}
