package com.prgers.teacher.controller;


import com.prgers.common.result.Result;
import com.prgers.teacher.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author prgers
 * @since 2021-01-15
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class EduSubjectController {

    private EduSubjectService eduSubjectService;

    @ApiOperation("课程导入")
    @PostMapping("/import")
    public Result importSubjectByFile(@RequestParam("file") MultipartFile file) {

        List<String> errorMessage = eduSubjectService.importSubjectFromEXL(file);
        if (errorMessage.size() == 0) {
            return Result.ok();
        }else {
            return  Result.error().data("message", errorMessage);
        }

    }


    public EduSubjectController(){};

    @Autowired
    public EduSubjectController(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }
}

