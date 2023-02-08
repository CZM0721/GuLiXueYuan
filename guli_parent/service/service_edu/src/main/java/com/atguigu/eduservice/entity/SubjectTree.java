package com.atguigu.eduservice.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SubjectTree对象", description="课程分类树")
public class SubjectTree {

    private String id;
    private String title;
    private List<SubjectTree> children;

}
