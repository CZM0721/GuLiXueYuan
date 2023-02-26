package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CouresVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/saveCourse")
    public R saveCoursec(@RequestBody CouresVo couresVo){
        return eduCourseService.saveCouresInfo(couresVo);
    }

    @PostMapping("/updateCourse")
    public R updateCourse(@RequestBody CouresVo couresVo){
        return eduCourseService.updateCourse(couresVo);
    }

    @GetMapping("/getCourse/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        return eduCourseService.getCourseInfo(courseId);
    }

}

