package com.ninep.course.service;

import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.CourseChapterEditDTO;
import com.ninep.course.admin.DTO.CourseChapterSaveDTO;
import com.ninep.course.entity.CourseChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.course.admin.response.CourseChapterPageResp;
import com.ninep.course.admin.DTO.CourseChapterPageDTO;

/**
 * <p>
 * 章节信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface ICourseChapterService extends IService<CourseChapter> {

    Page<CourseChapterPageResp> pageList(CourseChapterPageDTO pageVo);

    Result<String> saveChapter(CourseChapterSaveDTO saveVo);

    Result<String> edit(CourseChapterEditDTO editVo);

    Result<String> delete(Long id);
}
