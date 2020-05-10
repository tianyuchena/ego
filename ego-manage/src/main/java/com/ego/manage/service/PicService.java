package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/10 9:03
 * @Description: 文件上传服务层接口
 * @version: 1.0
 */
public interface PicService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String, Object> upload(MultipartFile file) throws IOException;
}
