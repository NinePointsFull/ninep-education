package com.ninep.api.course.fallback;

import com.ninep.api.course.FeignCourseService;
import com.ninep.common.DTO.CourseViewDTO;
import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeignCourseServiceFallback implements FallbackFactory<FeignCourseService> {
    private static final Logger log = LoggerFactory.getLogger(FeignCourseServiceFallback.class);
    @Override
    public FeignCourseService create(Throwable cause) {
        log.error("Course调用失败",cause.getMessage());
        return new FeignCourseService() {
            @Override
            public Result<Boolean> deleteByLecturerId(Long lecturedId) {
                return Result.success(false);
            }

            @Override
            public List<CourseViewDTO> view(List<Long> courseIds) {
                return new ArrayList<>();
            }

            @Override
            public int binding(UserCourseBindingDTO bindingDTO) {
                return 0;
            }
        };
    }
}
