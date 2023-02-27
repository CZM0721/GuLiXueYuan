package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CouresVo;
import com.atguigu.eduservice.entity.vo.PublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper,EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 保存课程以及简介
     * @param couresVo
     * @return
     */
    @Override
    public R saveCouresInfo(CouresVo couresVo) {
        //保存课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(couresVo,eduCourse);
        boolean save = this.save(eduCourse);
        if(save){
            // 课程保存成功并保存课程简介
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            // 获取课程id
            eduCourseDescription.setId(eduCourse.getId());
            BeanUtils.copyProperties(couresVo, eduCourseDescription);
            eduCourseDescriptionService.save(eduCourseDescription);
            return R.ok().data("id",eduCourse.getId());
        }
        return R.error().data("20004","保存失败");
    }

    /**
     * 获取课程以及简介
     * @param courseId 课程id
     * @return
     */
    @Override
    public R getCourseInfo(String courseId) {
        CouresVo couresVo = new CouresVo();
        EduCourse eduCourse = this.getById(courseId);
        EduCourseDescription byId = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourse,couresVo);
        BeanUtils.copyProperties(byId,couresVo);
        return R.ok().data("data",couresVo);
    }

    /**
     * 修改课程信息
     * @param couresVo
     * @return
     */
    @Override
    public R updateCourse(CouresVo couresVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourse.setId(couresVo.getCourseId());
        eduCourseDescription.setId(couresVo.getCourseId());
        BeanUtils.copyProperties(couresVo,eduCourse);
        BeanUtils.copyProperties(couresVo,eduCourseDescription);
        this.updateById(eduCourse);
        eduCourseDescriptionService.updateById(eduCourseDescription);
        return R.ok();
    }

    /**
     * 获取大纲信息
     * @param courseId
     * @return
     */
    @Override
    public R getPublishVo(String courseId) {
        PublishVo publishInfo = this.baseMapper.getPublishInfo(courseId);
        return R.ok().data("coursePublish",publishInfo);
    }

    /**
     * 获取已经发布的课程列表
     * @return
     */
    @Override
    public R getCourseList(Page page,PublishVo publishVo) {
        IPage<PublishVo> courseInfo = this.baseMapper.getCourseInfo(page,publishVo);
        return R.ok().data("data",courseInfo);
    }

}
