package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.Tree;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private VodClient vodClient;

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
            eduVideoQueryWrapper.eq("chapter_id",eduChapter.getId());
            List<EduVideo> list = eduVideoService.list(eduVideoQueryWrapper);
            tree.setIndex(0);
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
        QueryWrapper<EduVideo> eduVideoQueryWrapper1 = new QueryWrapper<>();
        eduVideoQueryWrapper1.select("video_source_id");
        eduVideoQueryWrapper1.eq("chapter_id",chapterId);
        List<EduVideo> list = eduVideoService.list(eduVideoQueryWrapper1);
        ArrayList<String> strings = new ArrayList<>();
        // 获取视频id集合
        if(!StringUtils.isEmpty(list)){
            list.forEach(eduVideo->{
                strings.add(eduVideo.getVideoSourceId());
            });
        }
        if (!StringUtils.isEmpty(strings)){
            // 删除小结下的视频
            String ids = strings.stream().collect(Collectors.joining(","));
            vodClient.delAliyunVod(ids);
        }
        // 删除章节下的小结
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        eduVideoService.remove(eduVideoQueryWrapper);
        // 根据章节id删除章节
        int i = this.baseMapper.deleteById(chapterId);
    }

}
