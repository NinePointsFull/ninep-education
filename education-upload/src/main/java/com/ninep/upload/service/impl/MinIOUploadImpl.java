package com.ninep.upload.service.impl;

import com.ninep.common.DTO.UploadDTO;
import com.ninep.upload.service.UploadPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fengyw
 */
@Slf4j
@Component(value = "minio")
public class MinIOUploadImpl implements UploadPicService {

    @Override
    public String uploadPic(MultipartFile file, UploadDTO upload) {
        return null;
    }



}
