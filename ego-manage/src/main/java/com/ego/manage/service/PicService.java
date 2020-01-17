package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PicService {
    /**
     * 上传图片
     * @return
     */
    Map<String,Object> uploadImg(MultipartFile imgFile) throws IOException;
}
