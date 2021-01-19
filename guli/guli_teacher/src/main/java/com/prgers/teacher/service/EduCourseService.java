package com.prgers.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.prgers.teacher.entity.query.CourseQuery;
import com.prgers.teacher.entity.vo.CourseDesc;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author prgers
 * @since 2021-01-16
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程基本信息
     * @param courseDesc 课程信息和描述的包装对象
     * @return 课程ID
     */
    String saveCourseDesc(CourseDesc courseDesc);

    /**
     * 根据id获取课程的基本信息和课程描述
     * @param id
     * @return vo
     */
    CourseDesc getCourseDescById(String id);

    /**
     * 修改课程的基本信息
     * @param courseDesc
     * @return
     */
    boolean updateCoureseById(CourseDesc courseDesc);

    /**
     * 根据条件查询课程列表
     * @param courseQuery
     * @param pageParam
     */
    void pageQueryList(CourseQuery courseQuery, Page<EduCourse> pageParam);

    /**
     * 根据ID删除课程基本信息
     * @param courseId
     * @return
     */
    boolean deleteCourseAndDescById(String courseId);
}
