package com.ninep.course.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ninep.common.cache.CacheRedis;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.ConstantsUtil;
import com.ninep.course.auth.vo.AuthUserStudyVo;
import com.ninep.course.entity.Resource;
import com.ninep.course.entity.UserStudy;
import com.ninep.course.mapper.ResourceMapper;
import com.ninep.course.mapper.UserStudyMapper;
import com.ninep.course.service.IUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 课程用户学习日志 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class UserStudyServiceImpl extends ServiceImpl<UserStudyMapper, UserStudy> implements IUserStudyService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private  CacheRedis cacheRedis;


    @Override
    public String study(AuthUserStudyVo authUserStudyVo) {
        // 资源信息
        Resource resource = getByResource(authUserStudyVo);
        if (ObjectUtil.isEmpty(resource)) {
            throw new BaseException("resourceId不正确");
        }
        if (new BigDecimal(resource.getVideoLength()).subtract(authUserStudyVo.getCurrentDuration()).intValue() < 1) {
            // 若视频时长-观看时长<1s,则认为观看完成
            UserStudy userStudy = getUserStudy(authUserStudyVo);
            if (ObjectUtil.isEmpty(userStudy)) {
                throw new BaseException("studyId不正确");
            }
            if (userStudy.getProgress().compareTo(BigDecimal.valueOf(100)) < 0) {
                // 更新进度
                userStudy.setProgress(BigDecimal.valueOf(100));
            }
            // 更新观看记录
            this.updateById(userStudy);
            // 清空缓存
            cacheRedis.delete(ConstantsUtil.RedisPre.USER_STUDY + authUserStudyVo.getStudyId());
            cacheRedis.delete(ConstantsUtil.RedisPre.PROGRESS + authUserStudyVo.getStudyId());
            return "OK";
        }
        // 没观看完成，进度存入redis，如没看完，定时任务处理
        authUserStudyVo.setTotalDuration(new BigDecimal(resource.getVideoLength()));
        cacheRedis.set(ConstantsUtil.RedisPre.PROGRESS + authUserStudyVo.getStudyId(), authUserStudyVo, 1, TimeUnit.DAYS);
        return "学习中";
    }

    private Resource getByResource(AuthUserStudyVo authUserStudyVo) {
        Resource resource = cacheRedis.getByJson(ConstantsUtil.RedisPre.RESOURCE + authUserStudyVo.getResourceId(), Resource.class);
        if (ObjectUtil.isEmpty(resource)) {
            resource = resourceMapper.selectById(authUserStudyVo.getResourceId());
            cacheRedis.set(ConstantsUtil.RedisPre.RESOURCE + authUserStudyVo.getResourceId(), resource, 1, TimeUnit.HOURS);
        }
        return resource;
    }

    private UserStudy getUserStudy(AuthUserStudyVo authUserStudyVo) {
        UserStudy userStudy = cacheRedis.getByJson(ConstantsUtil.RedisPre.USER_STUDY + authUserStudyVo.getStudyId(), UserStudy.class);
        if (ObjectUtil.isEmpty(userStudy)) {
            userStudy = this.getById(authUserStudyVo.getStudyId());
            cacheRedis.set(ConstantsUtil.RedisPre.USER_STUDY + authUserStudyVo.getStudyId(), userStudy, 1, TimeUnit.HOURS);
        }
        return userStudy;
    }
}
