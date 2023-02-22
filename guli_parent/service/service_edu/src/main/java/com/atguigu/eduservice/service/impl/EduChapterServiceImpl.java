package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.Tree;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 获取课程大纲树结构
     * @param courseId 课程id
     * @return
     */
    @Override
    public R getChapterTree(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        // 根据课程id查询章节数
        List<EduChapter> eduChapters = this.baseMapper.selectList(eduChapterQueryWrapper);
        ArrayList<Tree> trees = new ArrayList<>();
        for (EduChapter eduChapter: eduChapters) {
            Tree tree = new Tree();
            BeanUtils.copyProperties( eduChapter ,tree);
            // 根据章节id查询章节的小结数
            QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
            eduChapterQueryWrapper.eq("chapter_id",eduChapter.getId());
            List<EduVideo> list = eduVideoService.list(eduVideoQueryWrapper);
            tree.setChildren(list);
            trees.add(tree);
        }
        return R.ok().data("data",trees);
    }

    /**
     * 根据章节id删除章节以及章节下的小结
     * @param chapterId 章节id
     */
    @Override
    public void removeChapterByChapterId(String chapterId) {
        // 根据章节id删除章节
        int i = this.baseMapper.deleteById(chapterId);
        // 删除章节下的小结
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        eduVideoService.remove(eduVideoQueryWrapper);
    }

}
