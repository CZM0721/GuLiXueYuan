package com.atguigu.service_vdo.controller;

import com.atguigu.commonutils.R;
import com.atguigu.service_vdo.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    //删除上传视频到阿里云
    @DeleteMapping("/delAliyunVod/{id}")
    public R delAliyunVod(@PathVariable("id") String id){
        return vodService.remoceVodByID(id);
    }

}
