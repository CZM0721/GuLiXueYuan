package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CouresVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
public interface EduCourseService extends IService<EduCourse> {

    R saveCouresInfo(CouresVo couresVo);

    R getCourseInfo(String courseId);

    R updateCourse(CouresVo couresVo);

}
