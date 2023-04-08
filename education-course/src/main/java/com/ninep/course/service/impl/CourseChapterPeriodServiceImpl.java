package com.ninep.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.common.utils.AssertUtil;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterPeriodEditDTO;
import com.ninep.course.admin.DTO.CourseChapterPeriodSaveDTO;
import com.ninep.course.entity.CourseChapterPeriod;
import com.ninep.course.entity.UserStudy;
import com.ninep.course.mapper.CourseChapterPeriodMapper;
import com.ninep.course.mapper.UserStudyMapper;
import com.ninep.course.service.ICourseChapterPeriodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课时信息 服务实现类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Service
public class CourseChapterPeriodServiceImpl extends ServiceImpl<CourseChapterPeriodMapper, CourseChapterPeriod> implements ICourseChapterPeriodService {

    @Resource
    private UserStudyMapper userStudyMapper;

    @Override
    public Result<String> savePeriod(CourseChapterPeriodSaveDTO saveVo) {
        AssertUtil.notNull(saveVo, SysConfigEnum.CONFIG_ERROR);
        CourseChapterPeriod courseChapterPeriod = BeanUtil.copyProperties(saveVo, CourseChapterPeriod.class);
        if (this.save(courseChapterPeriod)){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    public Result<String> edit(CourseChapterPeriodEditDTO editVo) {
        //前端待完善
        AssertUtil.notNull(editVo,SysConfigEnum.CONFIG_ERROR);
        AssertUtil.notNull(editVo.getId(),SysConfigEnum.CONFIG_ERROR);
        CourseChapterPeriod courseChapterPeriod = BeanUtil.copyProperties(editVo, CourseChapterPeriod.class);
        if (this.updateById(courseChapterPeriod)){
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }

    @Override
    public Result<String> delete(Long id) {
        //TODO 前端待完善
        AssertUtil.notNull(id,SysConfigEnum.CONFIG_ERROR);
        if (this.removeById(id)){
            //删除学习进度
            userStudyMapper.delete(new LambdaQueryWrapper<UserStudy>().eq(UserStudy::getPeriodId,id));
            return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
        }
        throw new BaseException(ResultEnum.OTHER_ERROR.getMsg());
    }
}
