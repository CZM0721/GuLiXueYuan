package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.SubjectTree;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //获取课程分类树
    @Override
    public List<SubjectTree> getTree() {
        // 获取课程树的一级分类
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id",'0');
        List<EduSubject> eduSubjects = this.baseMapper.selectList(eduSubjectQueryWrapper);
        List<SubjectTree> objects = new ArrayList<>();
        for (int i = 0; i < eduSubjects.size(); i++) {
            SubjectTree subjectTree = new SubjectTree();
            BeanUtils.copyProperties(eduSubjects.get(i),subjectTree );
            objects.add(subjectTree);
        }
        return getNode(objects);
    }

    //获取树的子节点
    public List<SubjectTree> getNode(List<SubjectTree> subjectTrees){
        for (int i = 0; i < subjectTrees.size(); i++) {
            QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
            eduSubjectQueryWrapper.eq("parent_id", subjectTrees.get(i).getId());
            List<EduSubject> nodes = this.baseMapper.selectList(eduSubjectQueryWrapper);
            List<SubjectTree> objects = new ArrayList<>();
            for (int j = 0; j < nodes.size(); j++) {
                SubjectTree subjectTree = new SubjectTree();
                BeanUtils.copyProperties(nodes.get(j),subjectTree);
                objects.add(subjectTree);
            }
            if (objects.size() == 0){
                subjectTrees.get(i).setChildren(objects);
                break;
            }else {
                subjectTrees.get(i).setChildren(objects);
                getNode(objects);
            }
        }
            return subjectTrees;
    }
}
