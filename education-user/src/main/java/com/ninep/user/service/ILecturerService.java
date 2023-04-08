package com.ninep.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ninep.common.utils.Page;
import com.ninep.user.admin.DTO.LecturerEditDTO;
import com.ninep.user.admin.DTO.LecturerPageDTO;
import com.ninep.user.admin.DTO.LecturerSaveDTO;
import com.ninep.user.admin.response.LecturerPageResp;
import com.ninep.user.entity.Lecturer;

/**
 * <p>
 * 讲师信息 服务类
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
public interface ILecturerService extends IService<Lecturer> {

    Page<LecturerPageResp> pageList(LecturerPageDTO pageVo);


    String edit(LecturerEditDTO lecturerEditDTO);

    void delete(Long id);

    String save(LecturerSaveDTO lecturerSaveDTO);
}
