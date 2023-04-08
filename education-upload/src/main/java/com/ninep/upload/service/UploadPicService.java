package com.ninep.upload.service;

import com.ninep.common.DTO.UploadDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传功能
 */
public interface UploadPicService {

    /**
     * 图片上传
     *
     * @return 图片url
     */
    String uploadPic(MultipartFile file, UploadDTO upload);
}
