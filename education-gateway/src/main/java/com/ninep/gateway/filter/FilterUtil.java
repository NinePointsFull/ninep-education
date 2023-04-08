package com.ninep.gateway.filter;

import lombok.extern.slf4j.Slf4j;

/**
 * 过滤器工具类
 *
 * @author NineP
 */
@Slf4j
public final class FilterUtil {

    private FilterUtil() {
    }

    /**
     * 技术文档
     */
    public static final String API_V2 = "/v2";
    /**
     * 图片
     */
    public static final String IMAGES = "/images";
    /**
     * Api路径前缀
     */
    public static final String API_URL_PREFIX = "/api";
    /**
     * Boss路径前缀
     */
    public static final String CALLBACK_URL_PREFIX = "/callback";
    /**
     * Admin路径前缀
     */
    public static final String ADMIN_URL_PREFIX = "/admin";

    /**
     * 校验uri里面的第二个斜杠的字符串
     *
     * @param uri 请求URL
     * @param key 需要校验的字符串
     * @return 校验结果
     */
    public static boolean checkUri(String uri, String key) {
        String result = uri.substring(uri.indexOf("/", uri.indexOf("/") + 1));
        return result.startsWith(key);
    }

    public static boolean startsWith(String uri, String key) {
        return uri.startsWith(key);
    }
}
