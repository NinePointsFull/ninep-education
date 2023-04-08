package com.ninep.upload.factory;

import com.ninep.common.enums.StoragePlatformEnum;
import com.ninep.common.exception.BaseException;
import com.ninep.upload.service.UploadPicService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工厂模式结合策略模式，根据storagePlatform得到对应实现类
 */
@Component
public class UploadFactory {

    @Resource
    private Map<String, UploadPicService> uploadFaceMap;

    public UploadPicService getUpload(Integer storagePlatform){
        if (StoragePlatformEnum.ALIYUN.getCode().equals(storagePlatform)){
            return uploadFaceMap.get(StoragePlatformEnum.ALIYUN.getMode());
        }
        if (StoragePlatformEnum.MinIO.getCode().equals(storagePlatform)){
            return uploadFaceMap.get(StoragePlatformEnum.MinIO.getMode());
        }
        if (StoragePlatformEnum.LOCAL.getCode().equals(storagePlatform)){
            return uploadFaceMap.get(StoragePlatformEnum.LOCAL.getMode());
        }
        if (StoragePlatformEnum.TENCENT.getCode().equals(storagePlatform)){
            return uploadFaceMap.get(StoragePlatformEnum.TENCENT.getMode());
        }
        throw new BaseException("配置异常！");
    }
}
