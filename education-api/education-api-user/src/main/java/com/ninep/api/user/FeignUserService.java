package com.ninep.api.user;

import com.ninep.api.user.fallback.FeignUserServiceFallback;
import com.ninep.common.DTO.LecturerViewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author NineP
 */

@FeignClient(value = "user-service",fallbackFactory = FeignUserServiceFallback.class)
public interface FeignUserService {

    @GetMapping("/user/admin/lecturer/list")
    Map<Long,String> list();

    @GetMapping("/user/admin/lecturer/detail")
    LecturerViewDTO detail(@RequestParam("lecturerId") Long lecturerId);


}
