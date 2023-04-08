package com.ninep.course.admin.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author NineP
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "VodConfigResp", description = "Admin-上传音视频配置")
public class VodConfigResp {

    @ApiModelProperty(value = "使用平台")
    private Integer vodPlatform;

    private PolyvConfig polyvConfig;
    //private TencentConfig tencentConfig;
    private PriConfig priConfig;
    private String vodSign;

    /**
     * 保利威
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PolyvConfig {

        @ApiModelProperty(value = "账号ID")
        private String userid;

        @ApiModelProperty(value = "时间戳")
        private Long ptime;

        @ApiModelProperty(value = "sign")
        private String sign;

        @ApiModelProperty(value = "hash")
        private String hash;

        @ApiModelProperty(value = "点播分类ID")
        private Long cataid = 1L;
    }

    /**
     * 私有化
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriConfig {
        // TODO
        @ApiModelProperty(value = "sign")
        private String sign;
    }

   /* @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TencentConfig {

    }*/
}
