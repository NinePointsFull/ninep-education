package com.ninep.common.utils;

import com.ninep.common.DTO.VodDTO;
import com.qcloud.vod.VodUploadClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DescribeStorageDataRequest;
import com.tencentcloudapi.vod.v20180717.models.DescribeStorageDataResponse;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

@Slf4j
public final class TencentUtil {
    public static  String getSign(String secretId,String secretKey){
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(secretId);
        sign.setSecretKey(secretKey);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        // 签名有效期：2天
        sign.setSignValidDuration(3600 * 24 * 2);


        try {
            String signature = sign.getUploadSignature();
            return signature;
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }
        throw null;
    }

    public static VodUploadClient getVodUploadClient(String secretId,String secretKey){
        return new VodUploadClient(secretId,secretKey);
    }

    public static void upload(VodUploadClient client){

        //TODO

    }

    /**
     * 获取存储空间
     * @return
     * @throws TencentCloudSDKException
     */
    public static DescribeStorageDataResponse getDescribeStorageDataResponse(VodDTO vodConfig) throws TencentCloudSDKException {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(vodConfig.getTencentkey(), vodConfig.getTencentSecret());
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("vod.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        VodClient client = new VodClient(cred, "", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        DescribeStorageDataRequest req = new DescribeStorageDataRequest();

        // 返回的resp是一个DescribeStorageDataResponse的实例，与请求对象对应
        DescribeStorageDataResponse resp = client.DescribeStorageData(req);
        return resp;
    }




    // 签名工具类
    static class Signature {
        private String secretId;
        private String secretKey;
        private long currentTime;
        private int random;
        private int signValidDuration;


        private static final String HMAC_ALGORITHM = "HmacSHA1"; //签名算法
        private static final String CONTENT_CHARSET = "UTF-8";


        public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
            byte[] byte3 = new byte[byte1.length + byte2.length];
            System.arraycopy(byte1, 0, byte3, 0, byte1.length);
            System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
            return byte3;
        }


        // 获取签名
        public String getUploadSignature() throws Exception {
            String strSign = "";
            String contextStr = "";


            // 生成原始参数字符串
            long endTime = (currentTime + signValidDuration);
            contextStr += "secretId=" + java.net.URLEncoder.encode(secretId, "utf8");
            contextStr += "&currentTimeStamp=" + currentTime;
            contextStr += "&expireTime=" + endTime;
            contextStr += "&random=" + random;


            try {
                Mac mac = Mac.getInstance(HMAC_ALGORITHM);
                SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
                mac.init(secretKey);


                byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
                byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
                strSign = base64Encode(sigBuf);
                strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
            } catch (Exception e) {
                throw e;
            }
            return strSign;
        }


        private String base64Encode(byte[] buffer) {
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(buffer);
        }


        public void setSecretId(String secretId) {
            this.secretId = secretId;
        }


        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }


        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }


        public void setRandom(int random) {
            this.random = random;
        }


        public void setSignValidDuration(int signValidDuration) {
            this.signValidDuration = signValidDuration;
        }

    }
}

