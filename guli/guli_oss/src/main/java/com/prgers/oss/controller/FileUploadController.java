package com.prgers.oss.controller;

import com.prgers.common.result.Result;
import com.prgers.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result uploadFile(@RequestParam("file") MultipartFile file) {

        String fileUrl = fileService.uploadFile(file);

        return Result.ok().data("avatar", fileUrl);
    }

    public FileUploadController(){};

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }
}
