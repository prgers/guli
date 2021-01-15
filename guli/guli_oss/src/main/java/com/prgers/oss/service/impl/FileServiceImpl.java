package com.prgers.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.prgers.oss.service.FileService;
import com.prgers.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file) {
        OSSClient ossClient = null;
        String fileUrl = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClient(ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //获取文件名称
            String filename = file.getOriginalFilename();
            //获取文件后缀
            String ext = filename.substring(filename.lastIndexOf("."));
            //设置文件新名称
            String newName = UUID.randomUUID().toString().replace("-","") + ext;
            //根据年月日来穿件文件夹
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接一个完整的路径
            String urlPath = ConstantPropertiesUtil.FILE_HOST + "/" + datePath + "/" + newName;

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, urlPath, inputStream);
            fileUrl = "https://" + ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + urlPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }


        return fileUrl;
    }
}
