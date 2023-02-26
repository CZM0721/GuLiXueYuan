package com.atguigu.eduservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Tree对象", description="树结构")
public class Tree<T> {

    private String id;
    private String title;
    private Integer index;
    private List<T> children;

}
