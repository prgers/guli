package com.prgers.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.common.result.Result;
import com.prgers.teacher.entity.EduCourse;
import com.prgers.teacher.entity.EduTeacher;
import com.prgers.teacher.entity.query.CourseQuery;
import com.prgers.teacher.entity.vo.CourseDesc;
import com.prgers.teacher.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */

@Api(tags = "课程管理")
@RestController
@RequestMapping("/course")
@CrossOrigin
public class EduCourseController {

    private EduCourseService eduCourseService;
    @ApiOperation("保存课程")
    @PostMapping("/saveCourse")
    public Result saveCourse(@RequestBody CourseDesc courseDesc) { //接收课程和课程描述信息
        try {
            String courseId = eduCourseService.saveCourseDesc(courseDesc);
            return Result.ok().data("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation("根据ID删除课程信息")
    @DeleteMapping("/deleteCourse/{courseId}")
    public Result deleteCoureseById(@PathVariable String courseId){
        boolean result = eduCourseService.deleteCourseAndDescById(courseId);
        if (result){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("修改课程信息和描述")
    @PutMapping("/updateCoureDesc")
    public Result updateCoureseById(@RequestBody CourseDesc courseDesc) {
        boolean flag = eduCourseService.updateCoureseById(courseDesc);
        if (flag) {
            return Result.ok().data("courseId",courseDesc.getEduCourse().getId());
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据ID获取课程基本信息和描述")
    @GetMapping("/getCourseAndDescById/{id}")
    public Result getCourseDescById(@PathVariable String id) {
        CourseDesc courseDesc = eduCourseService.getCourseDescById(id);
        return Result.ok().data("item", courseDesc);
    }

    @ApiOperation("根据条件查询并分页课程列表")
    @PostMapping("/{page}/{limit}")
    public Result pageQuery(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQuery courseQuery) {

        Page<EduCourse> pageParam = new Page<>(page,limit);
        eduCourseService.pageQueryList(courseQuery, pageParam);
        return  Result.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    public EduCourseController(){};

    @Autowired
    public EduCourseController(EduCourseService eduCourseService) {
        this.eduCourseService = eduCourseService;
    }
}

