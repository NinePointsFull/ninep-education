package com.ninep.upload.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.upload.service.UploadPicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author fengyw
 */
@Slf4j
@Component(value = "local")
public class LocalUploadImpl implements UploadPicService {

    public static final String LOCALPATH = System.getProperty("user.dir") + "/files/images/";
    public static final String PATH = "/system/images/";

    @Override
    public String uploadPic(MultipartFile file, UploadDTO upload) {
        String fileName = IdUtil.simpleUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        try {
            file.transferTo(FileUtil.file(FileUtil.mkdir(LOCALPATH), fileName));
        } catch (IOException e) {
            log.error("本地上传错误", e);
            return "";
        }
        return upload.getWebsiteDomain() + "gateway" + PATH + fileName;
    }

}
