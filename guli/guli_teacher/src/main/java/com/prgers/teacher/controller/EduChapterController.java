package com.prgers.teacher.controller;


import com.prgers.common.result.Result;
import com.prgers.teacher.entity.EduChapter;
import com.prgers.teacher.entity.vo.EduChapterVo;
import com.prgers.teacher.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/chapter")
@CrossOrigin
public class EduChapterController {

    private EduChapterService chapterService;

    @ApiOperation("保存章节")
    @PostMapping("/saveChapter")
    public Result saveChapter(@RequestBody EduChapter chapter) {
        boolean save = chapterService.save(chapter);
        if (save) {
            return Result.ok().message("保存成功");
        }else {
            return Result.error().message("保存失败");
        }
    }

    @ApiOperation("根据课程ID查询章节、小节列表")
    @GetMapping("/chapterList/{courseId}")
    public Result getChapterListById(@PathVariable String courseId) {
        List<EduChapterVo> list =  chapterService.getChapterListById(courseId);
        return Result.ok().data("items", list);
    }

    public EduChapterController(){};

    @Autowired
    public EduChapterController(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }

}

