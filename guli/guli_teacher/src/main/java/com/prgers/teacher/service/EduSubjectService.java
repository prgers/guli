package com.prgers.teacher.service;

import com.prgers.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.prgers.teacher.entity.vo.FirstClassSubject;
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

    /**
     * 获取课程分类列表
     * @return vo
     */
    List<FirstClassSubject> getSubjectTree();

    /**
     * 保存分类
     * @param eduSubject
     * @return
     */
    boolean saveFirstClass(EduSubject eduSubject);

    /**
     * 根据ID来删除分类
     * @param id
     * @return
     */
    boolean deleteSubjectById(String id);

//    /**
//     * 保存二级分类
//     * @param eduSubject
//     * @return
//     */
//    boolean saveSecondClass(EduSubject eduSubject);
}
