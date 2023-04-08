package com.ninep.course.service;

import com.ninep.course.auth.vo.AuthUserStudyVo;
import com.ninep.course.entity.UserStudy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程用户学习日志 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IUserStudyService extends IService<UserStudy> {

    String study(AuthUserStudyVo authUserStudyVo);
}
