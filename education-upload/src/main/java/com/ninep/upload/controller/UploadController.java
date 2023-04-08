package com.ninep.upload.controller;


import com.ninep.api.system.FeignSystemService;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.utils.Result;
import com.ninep.upload.factory.UploadFactory;
import com.ninep.upload.service.UploadPicService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/system/admin/upload")
@Slf4j
public class UploadController {

    @Autowired
    private FeignSystemService feignSystemService;
    @Autowired
    private UploadFactory uploadFactory;

    @ApiOperation(value = "上传图片", notes = "服务端上传图片接口，只支持图片格式")
    @ApiImplicitParam(name = "picFile", value = "图片文件", dataType = "File", dataTypeClass = File.class, paramType = "query", required = true)
    @PostMapping(value = "/pic")
    public Result<String> uploadPic(@RequestParam(value = "picFile") MultipartFile picFile) {
        //获取配置
        Result<UploadDTO> result= feignSystemService.upload();
        UploadPicService uploadPicService = uploadFactory.getUpload(result.getData().getStoragePlatform());
        String url = uploadPicService.uploadPic(picFile, result.getData());
        return Result.success(url);
    }

    @GetMapping("/test")
    public String test(){
        return "12313";
    }
}
