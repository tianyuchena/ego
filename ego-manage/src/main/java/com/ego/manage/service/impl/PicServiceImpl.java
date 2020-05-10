package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/10 9:07
 * @Description: 图片上传服务层实现类
 * @version: 1.0
 */
@Service
public class PicServiceImpl implements PicService {

    @Value("${ftpclient.host}")
    private String host;
    @Value("${ftpclient.port}")
    private int port;
    @Value("${ftpclient.username}")
    private String username;
    @Value("${ftpclient.password}")
    private String password;
    @Value("${ftpclient.basepath}")
    private String basepath;
    @Value("${ftpclient.filepath}")
    private String filepath;

    @Override
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        String filename = IDUtils.genImageName() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, port, username, password, basepath, filepath, filename, file.getInputStream());
        Map<String, Object> map = new HashMap<>();
        if(result) {
            map.put("error", 0);
            map.put("url", "http://"+host+"/"+filename);
        }else{
            map.put("error", 1);
            map.put("message", "图片上传失败");
        }
        return map;
    }
}
