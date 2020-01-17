package com.ego.manage.service.impl;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    @Value("${ftpclient.basePath}")
    private String basePath;
    @Value("${ftpclient.filepath}")
    private String filePath;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @Override
    public Map<String,Object> uploadImg(MultipartFile imgFile) throws IOException {
        String fileName = IDUtils.genImageName()+imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, imgFile.getInputStream());
        Map<String,Object> map = new HashMap<>();
        if(result){
            map.put("error", 0);
            map.put("url","http://" + host + "/" + fileName);
        }else{
            map.put("error", 1);
            map.put("message", "图片上传失败!!!");
        }
        return map;
    }
}
