package com.ninep.api.system.fallback;

import com.ninep.api.system.FeignSystemService;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.utils.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeignSystemServiceFallback implements FallbackFactory<FeignSystemService> {
    @Override
    public FeignSystemService create(Throwable cause) {
        return new FeignSystemService() {
            @Override
            public Result<UploadDTO> upload() {
                return Result.success(new UploadDTO());
            }

            @Override
            public VodDTO vod() {
                return null;
            }

            @Override
            public Map<String, String> getByConfigType(Integer code) {
                return null;
            }

            @Override
            public String getwWbsiteDomainByKey(String key) {
                return null;
            }
        };
    }
}
