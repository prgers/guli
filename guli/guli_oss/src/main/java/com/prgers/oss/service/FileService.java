package com.prgers.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件到阿里云
     * @param file 上传文件
     * @return 图片路径
     */
    String uploadFile(MultipartFile file);
}
