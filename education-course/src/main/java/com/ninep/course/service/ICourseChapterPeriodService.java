package com.ninep.course.service;

import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterPeriodEditDTO;
import com.ninep.course.admin.DTO.CourseChapterPeriodSaveDTO;
import com.ninep.course.entity.CourseChapterPeriod;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课时信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface ICourseChapterPeriodService extends IService<CourseChapterPeriod> {

    Result<String> savePeriod(CourseChapterPeriodSaveDTO saveVo);

    Result<String> edit(CourseChapterPeriodEditDTO editVo);

    Result<String> delete(Long id);
}
