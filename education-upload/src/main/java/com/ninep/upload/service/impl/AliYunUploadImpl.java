package com.ninep.upload.service.impl;

import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.utils.AliyunUtil;
import com.ninep.upload.service.UploadPicService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component(value = "aliyun")
public class AliYunUploadImpl implements UploadPicService {
    @Override
    public String uploadPic(MultipartFile file, UploadDTO upload) {
        String ossUrl = AliyunUtil.getOssUrl(file, upload);
        return ossUrl;
    }
}
