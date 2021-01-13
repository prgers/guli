package com.prgers.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.teacher.entity.EduTeacher;
import com.prgers.teacher.entity.query.TeacherQuery;
import com.prgers.teacher.mapper.EduTeacherMapper;
import com.prgers.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author prgers
 * @since 2021-01-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 根据条件分页查询讲师列表
     * @param pageParam
     * @param teacherQuery
     */
    @Override
    public void pageQueryList(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        //判断是否有查询条件传过来
        if (teacherQuery == null) {
            //没有条件查询，直接分页
            baseMapper.selectPage(pageParam, null);
            return;
        }

        //创建条件查询对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //获取条件值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //根据条件值进行条件拼接
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }

        baseMapper.selectPage(pageParam, wrapper);
    }
}
