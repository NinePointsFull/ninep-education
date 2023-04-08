package com.ninep.course.service;

import com.ninep.common.utils.Page;
import com.ninep.course.admin.DTO.ZoneCourseEditDTO;
import com.ninep.course.admin.DTO.ZoneCoursePageDTO;
import com.ninep.course.entity.ZoneCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.course.admin.response.ZoneCoursePageResp;
import com.ninep.course.admin.DTO.ZoneCourseSaveDTO;

/**
 * <p>
 * 专区课程关联表 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IZoneCourseService extends IService<ZoneCourse> {

    Page<ZoneCoursePageResp> pageList(ZoneCoursePageDTO pageVo);

    String saveRelation(ZoneCourseSaveDTO saveVo);

    String delete(Long id);

    String edit(ZoneCourseEditDTO editVo);
}
