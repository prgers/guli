package com.prgers.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.common.result.Result;
import com.prgers.teacher.entity.EduTeacher;
import com.prgers.teacher.entity.query.TeacherQuery;
import com.prgers.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author prgers
 * @since 2021-01-05
 */

@Api(tags = "讲师管理")
@RestController
@RequestMapping("/teacher")
public class EduTeacherController {

    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/teacherList")
    public Result getTeacherList() {
        try {
            List<EduTeacher> teachers = eduTeacherService.list(null);
            return Result.ok().data("list", teachers);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean result = eduTeacherService.save(eduTeacher);
        if (result) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据ID来删除讲师")
    @DeleteMapping("/{id}")
    public Result deleteTeacherById(
            @ApiParam(name = "id", value = "要删除的讲师ID", required = true)
            @PathVariable String id) {

        boolean result = eduTeacherService.removeById(id);
        if (result) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation("根据ID来修改讲师信息")
    @PutMapping("/{id}")
    public Result updateTeacherById(@PathVariable String id,
                                    @RequestBody(required = true) EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacherById(@PathVariable String id) {
        try {
            EduTeacher teacher = eduTeacherService.getById(id);
            return Result.ok().data("item", teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation("分页查询讲师列表")
    @GetMapping("/{page}/{limit}")
    public Result teacherListByPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long size) {

        Page<EduTeacher> pageParam = new Page<>(page,size);
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> teachers = pageParam.getRecords();
        return Result.ok().data("list", teachers);
    }

    @ApiOperation("根据条件分页查询讲师列表")
    @PostMapping("/pageQuery/{page}/{limit}")
    public Result pageQuery(@PathVariable Long page,
                            @PathVariable(value = "limit") Long size,
                            @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, size);

        eduTeacherService.pageQueryList(pageParam, teacherQuery);
        List<EduTeacher> teachers = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total", total).data("items", teachers);
    }

    public EduTeacherController() {};

    @Autowired
    public EduTeacherController(EduTeacherService eduTeacherService) {
        this.eduTeacherService = eduTeacherService;
    }


}

