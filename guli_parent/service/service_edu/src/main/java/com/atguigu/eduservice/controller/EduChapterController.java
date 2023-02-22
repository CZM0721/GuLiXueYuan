package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.service.EduChapterService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapter/{courseId}")
    public R getChapter(@PathVariable("courseId") String courseId){
        return eduChapterService.getChapterTree(courseId);
    }

    @PostMapping("/addOrUpdateChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.saveOrUpdate(eduChapter);
        if (save) return R.ok();
        return R.error();
    }

    @DeleteMapping("/delChapter/{chapterId}")
    public R delChapter(@PathVariable("chapterId") String chapterId){
        eduChapterService.removeChapterByChapterId(chapterId);
        return R.ok();
    }

}

