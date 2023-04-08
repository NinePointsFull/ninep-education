package com.ninep.gateway.filter;

import com.baomidou.kisso.security.token.SSOToken;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.ConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author NineP
 */
@Slf4j
@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * admin不需要token校验的接口
     */
    private static final List<String> EXCLUDE_TOKEN_URL = Arrays.asList(
            // 登录接口
            "/system/admin/login/password"
    );

    /**
     * admin不需要权限校验的接口
     */
    private static final List<String> EXCLUDE_URL = Arrays.asList(
            // 登录获取菜单接口
            "/system/admin/sys/menu/user/list",
            // 登录获取权限接口
            "/system/admin/sys/menu/permission/list",
            // 登录获取当前用户接口
            "/system/admin/sys/user/current",
            // 上传接口
            "/system/admin/upload/pic"/*,
            "/course/admin/resource/vod/config"*/
    );



    /**
     * 优先级，order越大，优先级越低
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getPath().value();
        if (FilterUtil.checkUri(uri, FilterUtil.CALLBACK_URL_PREFIX)) {
            // 路径存在关键词：/callback，不鉴权
            return chain.filter(exchange);
        }
        if (FilterUtil.checkUri(uri, FilterUtil.API_URL_PREFIX)) {
            // 路径存在关键词：/api，不鉴权
            return chain.filter(exchange);
        }
        if (FilterUtil.checkUri(uri, FilterUtil.API_V2)) {
            // 路径存在关键词：/v2，不鉴权
            return chain.filter(exchange);
        }
        if (FilterUtil.checkUri(uri, FilterUtil.IMAGES)) {
            // 路径存在关键词：/images
            return chain.filter(exchange);
        }
        // 额外不需要token认证的接口
        if (EXCLUDE_TOKEN_URL.contains(uri)) {
            return chain.filter(exchange);
        }

        Long userId = getUserId(request);
        if (FilterUtil.checkUri(uri, FilterUtil.ADMIN_URL_PREFIX)) {
            // admin校验
            if (!stringRedisTemplate.hasKey(ConstantsUtil.RedisPre.ADMINI_MENU.concat(userId.toString()))) {
                throw new BaseException(ResultEnum.MENU_PAST);
            }
            String tk = (String) stringRedisTemplate.opsForValue().get(ConstantsUtil.RedisPre.ADMINI_MENU.concat(userId.toString()));
            // 校验接口是否有权限
            if (!checkUri(uri, tk)) {
                throw new BaseException(ResultEnum.MENU_NO);
            }
            // 更新时间，使用户菜单不过期
            stringRedisTemplate.opsForValue().set(ConstantsUtil.RedisPre.ADMINI_MENU.concat(userId.toString()), tk, 1, TimeUnit.DAYS);
        }
        request.mutate().header(ConstantsUtil.USER_ID, String.valueOf(userId));
        return chain.filter(exchange);
    }

    // 校验用户是否有权限
    private static Boolean checkUri(String uri, String tk) {
        if (StringUtils.hasText(uri) && uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        // 额外不需要权限校验的接口
        if (EXCLUDE_URL.contains(uri)) {
            return true;
        }
        // 权限校验
        uri = uri.substring(1).replace("/", ":");
        if (tk.contains(uri)) {
            return true;
        }

        log.info("用户没该权限点，{}", uri);
        return false;
    }

    private Long getUserId(ServerHttpRequest request) {
        // 头部
        String token = request.getHeaders().getFirst(ConstantsUtil.TOKEN);
        if (StringUtils.isEmpty(token)) {
            log.error("token不存在，请重新登录");
            throw new BaseException("token不存在，请重新登录");
        }

        // 解析 token
        SSOToken jwt = null;
        try {
            jwt = SSOToken.parser(token);
        } catch (Exception e) {
            log.error("token异常，token={}", token);
            throw new BaseException(ResultEnum.TOKEN_ERROR);
        }

        // 校验token
        if (null == jwt) {
            log.error("code:{},msg:{}",ResultEnum.TOKEN_ERROR.getCode(),ResultEnum.TOKEN_ERROR.getMsg());
            throw new BaseException(ResultEnum.TOKEN_ERROR);
        }
        Long userId = Long.valueOf(jwt.getId());
        if (userId <= 0) {
            log.error("code:{},msg:{}",ResultEnum.TOKEN_ERROR.getCode(),ResultEnum.TOKEN_ERROR.getMsg());
            throw new BaseException(ResultEnum.TOKEN_ERROR);
        }

        // 更新时间，使token不过期
        stringRedisTemplate.opsForValue().set(userId.toString(), token, ConstantsUtil.SESSIONTIME, TimeUnit.MINUTES);
        return userId;
    }

}
