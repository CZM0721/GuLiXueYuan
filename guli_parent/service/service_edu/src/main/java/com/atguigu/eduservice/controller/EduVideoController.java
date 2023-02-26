package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/addOrUpdateVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.saveOrUpdate(eduVideo);
        if (save) return R.ok();
        return R.error();
    }

    @DeleteMapping("/delVideoById/{id}")
    public R delVideoById(@PathVariable("id") String id){
        boolean b = eduVideoService.removeById(id);
        return R.ok().data("data",b);
    }

}

