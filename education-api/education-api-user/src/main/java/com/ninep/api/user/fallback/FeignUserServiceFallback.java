package com.ninep.api.user.fallback;

import com.ninep.api.user.FeignUserService;
import com.ninep.common.DTO.LecturerViewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FeignUserServiceFallback implements FallbackFactory<FeignUserService> {
    private static final Logger log = LoggerFactory.getLogger(FeignUserServiceFallback.class);
    @Override
    public FeignUserService create(Throwable cause) {
        log.error("User调用失败:{}", cause.getMessage());
        return new FeignUserService() {
            @Override
            public Map<Long, String> list() {
                return new HashMap<Long,String>();
            }

            @Override
            public LecturerViewDTO detail(Long lecturerId) {
                return new LecturerViewDTO();
            }
        };
    }
}
