package com.ninep.user.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.ninep.common.enums.ResultEnum;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.utils.Page;
import com.ninep.common.utils.Result;
import com.ninep.user.admin.DTO.LecturerEditDTO;
import com.ninep.user.admin.DTO.LecturerPageDTO;
import com.ninep.user.admin.DTO.LecturerSaveDTO;
import com.ninep.user.admin.response.LecturerPageResp;
import com.ninep.user.entity.Lecturer;
import com.ninep.user.service.ILecturerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 讲师信息 前端控制器
 * </p>
 *
 * @author NineP
 * @since 2022-12-02
 */
@Api(tags = "ADMIN-讲师信息")
@RestController
@RequestMapping("/user/admin/lecturer")
@Slf4j
public class LecturerController {
    @Resource
    private ILecturerService lecturerService;


    @ApiOperation(value = "讲师信息分页", notes = "讲师信息分页")
    @PostMapping(value = "/page")
    public Result<Page<LecturerPageResp>> page(@RequestBody LecturerPageDTO pageVo) {
        Page<LecturerPageResp> page=lecturerService.pageList(pageVo);
        return Result.success(page);
    }

    @ApiOperation(value = "讲师信息添加", notes = "讲师信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Validated LecturerSaveDTO lecturerSaveDTO) {
        return Result.success(lecturerService.save(lecturerSaveDTO));
    }


    @ApiOperation(value = "讲师信息修改", notes = "讲师信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid LecturerEditDTO lecturerEditDTO) {
        String result=lecturerService.edit(lecturerEditDTO);
        return Result.success(result);
    }


    @ApiOperation(value = "讲师信息删除", notes = "讲师信息删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        lecturerService.delete(id);
        return Result.success(ResultEnum.OTHER_SUCCESS.getMsg());
    }

    @GetMapping("/list")
    public Map<Long,String> list(){
        List<Lecturer> list = lecturerService.list();
        if (CollUtil.isEmpty(list)){
            log.error(SysConfigEnum.CONFIG_ERROR.getMsg());
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(Lecturer::getId,Lecturer::getLecturerName,(item1,item2)->item1));
    }

}
