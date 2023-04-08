package com.ninep.system.service.impl;

import cn.hutool.json.JSONObject;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.enums.VodPlatformEnum;
import com.ninep.common.utils.BaiJiaYunUtil;
import com.ninep.common.utils.PolyvVodUtil;
import com.ninep.common.utils.TencentUtil;
import com.ninep.system.admin.response.SysStatVodResp;
import com.ninep.system.service.ISysConfigService;
import com.ninep.system.service.ISysStatService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.models.DescribeStorageDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class SysStatServiceImpl implements ISysStatService {

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public SysStatVodResp vod() {
        SysStatVodResp resp = new SysStatVodResp();
        try {
            VodDTO vodConfig = sysConfigService.vod();
            if (VodPlatformEnum.POLYV.getCode().equals(vodConfig.getVodPlatform())){
                polyvVod(vodConfig,resp);
            } else if (VodPlatformEnum.BJYUN.getCode().equals(vodConfig.getVodPlatform())) {
                baiJiaYun(vodConfig,resp);
            }else if (VodPlatformEnum.TENCENT.getCode().equals(vodConfig.getVodPlatform())){
                tencentVod(vodConfig,resp);
            }else if (VodPlatformEnum.ALIYUN.getCode().equals(vodConfig.getVodPlatform())){
                aliyunVod(vodConfig,resp);
            }
            //TODO 其他存储平台

        } catch (Exception e) {
            log.error("获取异常", e);
        }
        return resp;
    }

    private void aliyunVod(VodDTO vodConfig, SysStatVodResp resp) {

    }

    /**
     * 腾讯云
     */
    private void tencentVod(VodDTO vodConfig, SysStatVodResp resp) {
        try {
            DescribeStorageDataResponse describeStorageDataResponse = TencentUtil.getDescribeStorageDataResponse(vodConfig);
            Long totalStorage = describeStorageDataResponse.getTotalStorage();
            //todo 总空间
            resp.setTotalSpace(new BigDecimal(1).setScale(2,BigDecimal.ROUND_HALF_UP));
            resp.setUsedSpace(new BigDecimal(totalStorage).divide(BigDecimal.valueOf(1024 * 1024*1024 )).setScale(2,BigDecimal.ROUND_HALF_UP));
            resp.setTotalFlow(new BigDecimal(1).setScale(2,BigDecimal.ROUND_HALF_UP));
            resp.setUsedFlow(new BigDecimal(0.1).setScale(2,BigDecimal.ROUND_HALF_UP));
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 百家云
     * @param vodConfig
     * @param resp
     */
    private void baiJiaYun(VodDTO vodConfig, SysStatVodResp resp) {
        JSONObject result = BaiJiaYunUtil.getUserMain(vodConfig);
        resp.setTotalSpace(result.getBigDecimal("total_storage").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setUsedSpace(result.getBigDecimal("used_storage").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setTotalFlow(result.getBigDecimal("total_flow_limit").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setUsedFlow(result.getBigDecimal("used_flow").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 保利威
     * @param vodConfig
     * @param resp
     * @return
     */
    private void polyvVod(VodDTO vodConfig,SysStatVodResp resp){
        JSONObject result = PolyvVodUtil.getUserMain(vodConfig.getPolyvUserId(), vodConfig.getPolyvSecretKey());
        resp.setTotalSpace(result.getBigDecimal("totalSpace").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setUsedSpace(result.getBigDecimal("usedSpace").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setTotalFlow(result.getBigDecimal("totalFlow").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        resp.setUsedFlow(result.getBigDecimal("usedFlow").divide(BigDecimal.valueOf(1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
        //resp.setEmail(result.getStr("email"));
    }

}
