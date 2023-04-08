package com.ninep.upload.service.impl;

import com.ninep.common.DTO.UploadDTO;
import com.ninep.upload.service.UploadPicService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("tencent")
public class TencentUploadImpl implements UploadPicService {
    @Override
    public String uploadPic(MultipartFile file, UploadDTO upload) {
        return null;
    }
}
