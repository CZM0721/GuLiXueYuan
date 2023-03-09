package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addOrUpdateVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.saveOrUpdate(eduVideo);
        if (save) return R.ok();
        return R.error();
    }

    @DeleteMapping("/delVideoById/{id}")
    public R delVideoById(@PathVariable("id") String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        // 删除小结下的视频
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            vodClient.delAliyunVod(eduVideo.getVideoSourceId());
        }
        boolean b = eduVideoService.removeById(id);
        return R.ok().data("data",b);
    }

}

