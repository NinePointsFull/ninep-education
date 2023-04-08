package com.ninep.course.service;

import com.ninep.common.utils.Page;
import com.ninep.course.admin.DTO.ZoneSaveDTO;
import com.ninep.course.api.response.ApiZoneResp;
import com.ninep.course.entity.Zone;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.course.admin.response.ZonePageResp;
import com.ninep.course.admin.DTO.ZoneEditDTO;
import com.ninep.course.admin.DTO.ZonePageDTO;

import java.util.List;

/**
 * <p>
 * 专区 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface IZoneService extends IService<Zone> {

    Page<ZonePageResp> pageList(ZonePageDTO pageVo);

    String saveZone(ZoneSaveDTO saveVo);

    String edit(ZoneEditDTO editVo);

    String delete(Long id);

    List<ApiZoneResp> listForZone();
}
