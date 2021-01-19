package com.prgers.teacher.service;

import com.prgers.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.prgers.teacher.entity.vo.EduChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id获取章节、小节列表
     * @param courseId
     * @return
     */
    List<EduChapterVo> getChapterListById(String courseId);
}
