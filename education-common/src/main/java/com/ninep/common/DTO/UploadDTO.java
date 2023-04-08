package com.ninep.common.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author NineP
 */
@Data
@Accessors(chain = true)
public class UploadDTO implements Serializable {

    private static final long serialVersionUID = 1195869049655301491L;

    /**
     * 存储平台
     */
    private Integer storagePlatform;

    /**
     * 站点域名
     */
    private String websiteDomain;

    /**
     * 阿里云
     */
    private String aliyunOssUrl;
    private String aliyunOssBucket;
    private String aliyunOssEndpoint;
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    /**
     * MinIO
     */
    private String minioDomain;
    private String minioBucket;
}
