package com.prgers.oss.controller;

import com.prgers.common.result.Result;
import com.prgers.oss.service.FileService;
import com.prgers.oss.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传")
@CrossOrigin //跨域
@RestController
@RequestMapping("/oss/file")
public class FileUploadController {

    private FileService fileService;


    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result uploadFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件上传路径", required = false)
            @RequestParam(value = "host",required = false) String host) {

        if (!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String fileUrl = fileService.uploadFile(file);

        return Result.ok().message("文件上传成功").data("avatar", fileUrl);
    }

    public FileUploadController(){};

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }
}
