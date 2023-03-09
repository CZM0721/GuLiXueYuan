package com.atguigu.service_vdo.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.ram.model.v20150501.DeleteAccessKeyRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.R;
import com.atguigu.service_vdo.service.VodService;
import com.atguigu.service_vdo.util.EdoPropertiesUtil;
import com.atguigu.service_vdo.util.InitAliyunClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传视频到阿里云服务器
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(EdoPropertiesUtil.ACCESS_KEY_ID,EdoPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据视频id删除阿里云的视频
     * @param id 视频id
     * @return
     */
    @Override
    public R remoceVodByID(String id) {
        // 初始化阿里云信息
        DefaultAcsClient client = InitAliyunClient.initClient(EdoPropertiesUtil.ACCESS_KEY_ID, EdoPropertiesUtil.ACCESS_KEY_SECRET);
        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
        deleteVideoRequest.setVideoIds(id);
        try {
            DeleteVideoResponse acsResponse = client.getAcsResponse(deleteVideoRequest);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除失败");
        }
    }


}
