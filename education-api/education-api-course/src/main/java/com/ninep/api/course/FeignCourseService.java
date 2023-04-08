package com.ninep.api.course;

import com.ninep.api.course.fallback.FeignCourseServiceFallback;
import com.ninep.common.DTO.CourseViewDTO;
import com.ninep.common.DTO.UserCourseBindingDTO;
import com.ninep.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "course-service",fallbackFactory = FeignCourseServiceFallback.class)
public interface FeignCourseService {

    @DeleteMapping("/course/admin/course/deleteByLecturerId")
    Result<Boolean> deleteByLecturerId(@RequestParam("lecturerId") Long lecturedId);

    @GetMapping("/course/admin/course/view")
    List<CourseViewDTO> view(@RequestParam("courseIds") List<Long> courseIds);

    @PutMapping(value = "/course/admin/userCourse/binding")
    int binding(@RequestBody UserCourseBindingDTO bindingDTO);
}
