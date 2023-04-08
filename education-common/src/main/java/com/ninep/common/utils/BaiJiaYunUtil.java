package com.ninep.common.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ninep.common.DTO.VodDTO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author NineP
 */
@Slf4j
public class BaiJiaYunUtil {
    public static final  String domain="e50020704";


    /**
     * 流量/空间统计
     * @param vodConfig
     * @return
     */
    public static JSONObject getUserMain(VodDTO vodConfig) {
        String requestUrl = vodConfig.getBaijyDomain()+"openapi/video_account/getAccountInfo";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("partner_id", vodConfig.getBaijyPartnerId());
        String timestamp= String.valueOf(System.currentTimeMillis());
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        paramMap.put("sign", stringToMD5("partner_id="+vodConfig.getBaijyPartnerId()+"&timestamp="+timestamp+"&partner_key="+vodConfig.getBaijyPartnerKey()));
        String result = HttpUtil.post(requestUrl, new HashMap<>(paramMap));
        JSONObject resultJson = JSONUtil.parseObj(result);
        return resultJson.getJSONObject("data");
    }


    public static JSONObject getUrl(){
        String requestUrl = "https://e50020704.at.baijiayun.com/openapi/video/getUploadUrl";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("partner_id", 50020704);
        paramMap.put("file_name","baijiayun");
        paramMap.put("definition","16");
        Long timestamp = System.currentTimeMillis()/1000;
        paramMap.put("timestamp", timestamp);
        paramMap.put("sign", stringToMD5("definition=16&file_name=baijiayun&partner_id=50020704&timestamp="+timestamp+"&partner_key=ihfqsO3B3yUmR05AGtuEwpO4+TxOWCMFCKF9uQ/G1KRGqNEtPavqrKu6BUnYFTaaAtR4DnKaDv6YM8naxNDNZJhiIM8K"));
        String result = HttpUtil.post(requestUrl, new HashMap<>(paramMap));
        JSONObject resultJson = JSONUtil.parseObj(result);
        return resultJson.getJSONObject("data");
    }


    public static String stringToMD5(String plainText) {
        byte[] mdBytes = null;
        try {
            mdBytes = MessageDigest.getInstance("MD5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在！");
        }
        String mdCode = new BigInteger(1, mdBytes).toString(16);

        if (mdCode.length() < 32) {
            int a = 32 - mdCode.length();
            for (int i = 0; i < a; i++) {
                mdCode = "0" + mdCode;
            }
        }
        //return mdCode.toUpperCase(); //返回32位大写
        return mdCode;            // 默认返回32位小写
    }

}
