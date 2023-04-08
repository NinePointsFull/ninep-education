package com.ninep.api.system;

import com.ninep.api.system.fallback.FeignSystemServiceFallback;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.DTO.VodDTO;
import com.ninep.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "system-service",fallbackFactory = FeignSystemServiceFallback.class)
public interface FeignSystemService {

    @GetMapping(value = "/system/admin/sys/config/upload")
    Result<UploadDTO> upload();


    @GetMapping("/system/admin/sys/config/vod/config")
    VodDTO vod();

    @GetMapping("/system/admin/sys/config/getByConfigType")
    Map<String ,String > getByConfigType(@RequestParam("code") Integer code);

    @GetMapping("/system/admin/sys/config/getwWbsiteDomainByKey")
    String getwWbsiteDomainByKey(@RequestParam("key") String key);
}
