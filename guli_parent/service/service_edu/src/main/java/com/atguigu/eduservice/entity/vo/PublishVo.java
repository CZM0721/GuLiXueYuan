package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublishVo {

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "大纲名称")
    private String title;

    @ApiModelProperty(value = "课程价格")
    private String price;

    @ApiModelProperty(value = "课程课时")
    private String lessonNum;

    @ApiModelProperty(value = "课程图片")
    private String cover;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "一级分类名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "二级分类名称")
    private String subjectLevelTwo;

}
