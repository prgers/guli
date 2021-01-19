package com.prgers.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.common.Exception.EduException;
import com.prgers.teacher.entity.EduCourse;
import com.prgers.teacher.entity.EduCourseDescription;
import com.prgers.teacher.entity.query.CourseQuery;
import com.prgers.teacher.entity.vo.CourseDesc;
import com.prgers.teacher.mapper.EduCourseMapper;
import com.prgers.teacher.service.EduCourseDescriptionService;
import com.prgers.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    private EduCourseDescriptionService eduCourseDescriptionService;
    /**
     * 保存课程基本信息
     * @param courseDesc 课程信息和描述的包装对象
     * @return 课程ID
     */
    @Override
    public String saveCourseDesc(CourseDesc courseDesc) {

        //添加课程的基本信息
        int insert = baseMapper.insert(courseDesc.getEduCourse());
        if (insert <= 0){
            throw new EduException(20001, "添加课程失败");
        }

        //获取课程的id
        String courseId = courseDesc.getEduCourse().getId();

        //将课程的id添加到课程描述对象中
        courseDesc.getEduCourseDescription().setId(courseId);

        boolean save = eduCourseDescriptionService.save(courseDesc.getEduCourseDescription());
        if (!save) {
            throw new EduException(20001, "添加描述失败");
        }
        return courseId;
    }

    /**
     * 根据id获取课程的基本信息和课程描述
     * @param id
     * @return vo
     */
    @Override
    public CourseDesc getCourseDescById(String id) {

        CourseDesc courseDesc = new CourseDesc();
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            throw new EduException(20001, "此课程不存在");
        }
        courseDesc.setEduCourse(eduCourse);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);

        if (eduCourseDescription != null) {
            courseDesc.setEduCourseDescription(eduCourseDescription);
        }
        return courseDesc;
    }

    /**
     * 修改课程的基本信息
     * @param courseDesc
     * @return
     */
    @Override
    public boolean updateCoureseById(CourseDesc courseDesc) {
        if (StringUtils.isEmpty(courseDesc.getEduCourse().getId())) {
            throw new EduException(20001,"没有课程编号修改失败");
        }

        int result = baseMapper.updateById(courseDesc.getEduCourse());
        if (result <= 0) {
            throw new EduException(20001,"课程信息修改失败");
        }

        boolean b = eduCourseDescriptionService.updateById(courseDesc.getEduCourseDescription());

        return b;
    }

    /**
     * 根据条件查询课程列表
     * @param courseQuery
     * @param pageParam
     */
    @Override
    public void pageQueryList(CourseQuery courseQuery, Page<EduCourse> pageParam) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }

        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();

        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id", subjectId);
        }

        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(title)) {
            wrapper.eq("title", title);
        }

        baseMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 根据ID删除课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public boolean deleteCourseAndDescById(String courseId) {

        //TODO 删除小结

        //TODO 删除章节

        //根据id删除课程描述
        boolean flag = eduCourseDescriptionService.removeById(courseId);
        if (!flag) {
            throw new EduException(20001,"删除课程失败");
        }

        int result = baseMapper.deleteById(courseId);

        if (result <= 0) {
            throw new EduException(20001, "删除课程失败");
        }
        return result == 1;
    }

    public EduCourseServiceImpl() {};

    @Autowired
    public EduCourseServiceImpl(EduCourseDescriptionService eduCourseDescriptionService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
    }
}
