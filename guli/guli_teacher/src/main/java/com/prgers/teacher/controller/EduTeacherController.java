package com.prgers.teacher.controller;


import com.prgers.teacher.entity.EduTeacher;
import com.prgers.teacher.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author prgers
 * @since 2021-01-05
 */
@RestController
@RequestMapping("/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/teacherList")
    public List<EduTeacher> getTeacherList() {

        return eduTeacherService.list(null);
    }
}

