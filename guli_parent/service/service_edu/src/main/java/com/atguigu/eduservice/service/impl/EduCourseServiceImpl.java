package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CouresVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

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
            return R.ok().data("20001",eduCourse.getId());
        }
        return R.error().data("20004","保存失败");
    }
}
