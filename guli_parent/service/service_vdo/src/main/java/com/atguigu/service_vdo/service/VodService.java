package com.atguigu.service_vdo.service;

import com.atguigu.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    // 根据视频id删除阿里云的视频
    R remoceVodByID(String id);
}
