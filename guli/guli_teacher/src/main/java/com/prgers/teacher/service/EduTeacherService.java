package com.prgers.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.prgers.teacher.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author prgers
 * @since 2021-01-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 根据条件分页查询讲师列表
     * @param pageParam
     * @param teacherQuery
     */
    void pageQueryList(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}
