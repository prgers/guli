package com.prgers.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prgers.common.Exception.EduException;
import com.prgers.teacher.entity.EduSubject;
import com.prgers.teacher.mapper.EduSubjectMapper;
import com.prgers.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author prgers
 * @since 2021-01-15
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 根据传入的Excel表格，将表格中的数据导入到数据库
     * @param file Excel文件
     * @return 错误信息
     */
    @Override
    public List<String> importSubjectFromEXL(MultipartFile file) {

        List<String> errorMessage = new ArrayList<>();
        try {
            //获取文件流
            InputStream inputStream = file.getInputStream();

            //创建 workbook
            Workbook workbook = new HSSFWorkbook(inputStream);

            //根据workbook获取工作区sheet
            Sheet sheet = workbook.getSheetAt(0);

            //根据sheet获取row
            //获取最后一行的索引位置(行数)
            int lastRowNum = sheet.getLastRowNum();

            //判断是否存在数据
            if (lastRowNum <= 1){
                errorMessage.add("请填写数据");
                return errorMessage;
            }

            //遍历行
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //5. 根据每一行获取列表数据
                //获取第一列的数据
                Cell firstCell = row.getCell(0);
                if (firstCell == null) {
                    errorMessage.add("第" + i + "行第一列为空");
                    //跳转到下一行进行操作
                    continue;
                }
                String firstCellValue = firstCell.getStringCellValue();
                if (StringUtils.isEmpty(firstCellValue)) {
                    errorMessage.add("第" + i + "行第一列的内容为空");
                    //跳转到下一行进行操作
                    continue;
                }

                //firstCellValue是一级分类的名称
                //判断数据库中是否存在这个一级分类的名称，如果不存在，就添加，存在就不添加
                EduSubject firstEduSubject = this.existFirstClassByClassName(firstCellValue);

                //为了创建二级分类，创建相关联的pid
                String pid = null;
                if (firstEduSubject == null) {
                    EduSubject firstClass = new EduSubject();
                    firstClass.setTitle(firstCellValue);
                    firstClass.setParentId("0");
                    firstClass.setSort(0);
                    baseMapper.insert(firstClass);
                    pid = firstClass.getId();
                }else {
                    pid = firstEduSubject.getId();
                }

                //获取第二列数据
                Cell secondCell = row.getCell(1);
                if (secondCell == null) {
                    errorMessage.add("第" + i + "行第二列为空");
                    //跳转到下一行进行操作
                    continue;
                }

                String secondCellValue = secondCell.getStringCellValue();
                if (StringUtils.isEmpty(secondCellValue)) {
                    errorMessage.add("第" + i + "行第二列的内容为空");
                    //跳转到下一行进行操作
                    continue;
                }

                //secondCellValue是二级分类的名称
                //判断数据库中是否存在这个二级分类的名称，如果不存在，就添加，存在就不添加
                EduSubject secondEduSubject = this.existSecondClassByClassName(secondCellValue, pid);
                if (secondEduSubject == null) {
                    EduSubject firstClass = new EduSubject();
                    firstClass.setTitle(secondCellValue);
                    firstClass.setParentId(pid);
                    firstClass.setSort(0);
                    baseMapper.insert(firstClass);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new EduException(20001,"表格数据导入错误");
        }
        return errorMessage;
    }

    /**
     * 根据名称和pid来获取二级分类的数据
     * @param className 分类名称
     * @param pid 一级分类ID
     * @return eduSubject
     */
    private EduSubject existSecondClassByClassName(String className, String pid) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", className);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return  eduSubject;
    }

    /**
     * 根据名称来获取一级分类数据
     * @param className 分类名称
     * @return eduSubject
     */
    private EduSubject existFirstClassByClassName(String className) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", className);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return  eduSubject;
    }
}
