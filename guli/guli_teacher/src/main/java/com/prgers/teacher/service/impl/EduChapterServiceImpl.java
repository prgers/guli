package com.prgers.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prgers.teacher.entity.EduChapter;
import com.prgers.teacher.entity.EduVideo;
import com.prgers.teacher.entity.vo.EduChapterVo;
import com.prgers.teacher.entity.vo.EduVideoVo;
import com.prgers.teacher.mapper.EduChapterMapper;
import com.prgers.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.prgers.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 根据课程id获取章节、小节列表
     * @param courseId
     * @return
     */
    @Override
    public List<EduChapterVo> getChapterListById(String courseId) {

        //创建集合
        List<EduChapterVo> chapterVoList = new ArrayList<>();

        //根据课程id查询章节列表
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("sort","id");
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapper);
        
        //遍历章节列表
        for (EduChapter eduChapter : eduChapterList) {
            //创建ChapterVo
            EduChapterVo chapterVo = new EduChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            //根据章节ID获取小节列表
            QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id", chapterVo.getId());
            videoWrapper.orderByDesc("sort","id");
            List<EduVideo> eduVideoList = videoService.list(videoWrapper);

            //遍历小节列表
            for (EduVideo eduVideo : eduVideoList) {

                //创建videoVo
                EduVideoVo videoVo = new EduVideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                chapterVo.getChildren().add(videoVo);
            }
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }
}
