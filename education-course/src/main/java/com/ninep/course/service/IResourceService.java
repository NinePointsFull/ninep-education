package com.ninep.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.course.admin.DTO.ResourceEditDTO;
import com.ninep.course.admin.DTO.ResourcePageDTO;
import com.ninep.course.admin.DTO.ResourceSaveDTO;
import com.ninep.course.admin.response.ResourcePageResp;
import com.ninep.course.admin.response.VodConfigResp;
import com.ninep.course.entity.Resource;

/**
 * <p>
 * 课程视频信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IResourceService extends IService<Resource> {

    Page<ResourcePageResp> pageList(ResourcePageDTO pageVo);

    Result<String> edit(ResourceEditDTO editVo);

    Result<String> delete(Long id);

    Result<VodConfigResp> getVodConfig();

    Result<String> saveResource(ResourceSaveDTO saveVo);
}
