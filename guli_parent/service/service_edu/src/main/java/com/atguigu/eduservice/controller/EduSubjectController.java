package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.SubjectTree;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //获取课程分类树
    @GetMapping("/getSubjectTree")
    public R getSubjectTree(){
        List<SubjectTree> subjectTrees = subjectService.getTree();
        return R.ok().data("data",subjectTrees);
    }

    //根据父亲节点id查询
    @GetMapping("/getSubjectByParentId")
    public R getSubjectByParentId(String parentId){
        return R.ok().data("data",this.subjectService.getNode(parentId));
    }

}

