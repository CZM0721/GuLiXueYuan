package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.PublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishVo getPublishInfo(String id);

    IPage<PublishVo> getCourseInfo(@Param("page") Page page,@Param("publishVo") PublishVo publishVo);

}
