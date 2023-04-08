package com.ninep.course.api.controller;

import com.ninep.common.utils.Result;
import com.ninep.course.auth.vo.AuthUserStudyVo;
import com.ninep.course.service.IUserStudyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API-课程用户学习日志
 *
 */
@Api(tags = "API-课程用户学习日志")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/api/user/study")
public class ApiUserStudyController {

    @Autowired
    private IUserStudyService userStudyService;

    /**
     * 课程信息列表接口
     * 视频播放过程中每过3s调一次，知道返回ok
     */
    @ApiOperation(value = "记录学习进度", notes = "记录学习进度")
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public Result<String> study(@RequestBody AuthUserStudyVo authUserStudyVo) {
        String result=userStudyService.study(authUserStudyVo);
        return Result.success(result);
    }

}
