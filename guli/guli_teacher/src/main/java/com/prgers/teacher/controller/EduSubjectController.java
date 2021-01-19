package com.prgers.teacher.controller;


import com.prgers.common.result.Result;
import com.prgers.teacher.entity.EduSubject;
import com.prgers.teacher.entity.vo.FirstClassSubject;
import com.prgers.teacher.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation("课程分类列表")
    @GetMapping("/list")
    public Result getTree(){
        List<FirstClassSubject> list = eduSubjectService.getSubjectTree();
        return Result.ok().data("subjectList", list);
    }

    @ApiOperation("添加分类")
    @PostMapping("/saveClass")
    public Result saveClass(@ApiParam(name = "eduSubject", value = "课程分类对象", required = true)
                                @RequestBody EduSubject eduSubject) {
        boolean result =  eduSubjectService.saveFirstClass(eduSubject);
        if (result) {
            return Result.ok();
        }else {
            return Result.error().message("保存失败");
        }
    }

//    @ApiOperation("添加二级分类")
//    @PostMapping("/saveSecondClass")
//    public Result saveSecondClass(@ApiParam(name = "eduSubject", value = "课程分类对象", required = true)
//                                  @RequestBody EduSubject eduSubject) {
//        boolean result =  eduSubjectService.saveSecondClass(eduSubject);
//        if (result) {
//            return Result.ok();
//        }else {
//            return Result.error().message("保存失败");
//        }
//    }

    @ApiOperation("根据ID来删除课程")
    @DeleteMapping("/{id}")
    public Result removeSubjectById(@PathVariable String id) {
        boolean result = eduSubjectService.deleteSubjectById(id);
        if (result) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

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

