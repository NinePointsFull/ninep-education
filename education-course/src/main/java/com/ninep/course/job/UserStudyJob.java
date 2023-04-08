package com.ninep.course.job;

import cn.hutool.core.collection.CollUtil;
import com.ninep.common.cache.CacheRedis;
import com.ninep.common.utils.ConstantsUtil;
import com.ninep.course.auth.vo.AuthUserStudyVo;
import com.ninep.course.entity.UserStudy;
import com.ninep.course.service.IUserStudyService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class UserStudyJob {
    @Autowired
    private IUserStudyService userStudyService;

    @Autowired
    private CacheRedis cacheRedis;

    /**
     * TODO
     * 每60秒执行一次
     */
    @XxlJob("userStudyJobHandler")
    public void progress() {
        // 处理学习进度
        Set<String> keys = cacheRedis.getStringRedisTemplate().keys(ConstantsUtil.RedisPre.PROGRESS + "*");
        if (CollUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                if (cacheRedis.getStringRedisTemplate().getExpire(key, TimeUnit.MINUTES) < 1439) {
                    // 默认过期时间为60分钟，若剩余时间小于59分，则处理
                    AuthUserStudyVo authUserStudyVo = cacheRedis.getByJson(key, AuthUserStudyVo.class);
                    UserStudy userStudy = userStudyService.getById(authUserStudyVo.getStudyId());
                    userStudy.setProgress(authUserStudyVo.getCurrentDuration().divide(authUserStudyVo.getTotalDuration(), BigDecimal.ROUND_CEILING).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP));
                    userStudyService.updateById(userStudy);
                    // 清除缓存
                    cacheRedis.delete(ConstantsUtil.RedisPre.USER_STUDY + authUserStudyVo.getStudyId());
                    cacheRedis.delete(key);
                }
            }
        }
        log.info("课程进度同步=>>>>");
        XxlJobHelper.handleSuccess("完成");
    }
}
