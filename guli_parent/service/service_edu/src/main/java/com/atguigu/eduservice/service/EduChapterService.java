package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 获取课程大纲树结构
     * @param courseId 课程id
     * @return
     */
    R getChapterTree(String courseId);

    /**
     * 根据章节id删除章节以及章节下的小结
     * @param chapterId
     */
    void removeChapterByChapterId(String chapterId);

}
