package com.prgers.teacher.service;

import com.prgers.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author prgers
 * @since 2021-01-15
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 根据传入的Excel表格，将表格中的数据导入到数据库
     * @param file Excel文件
     * @return 错误信息
     */
    List<String> importSubjectFromEXL(MultipartFile file);
}
