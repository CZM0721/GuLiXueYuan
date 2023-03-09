package com.atguigu.service_vdo.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitAliyunClient {

    public static DefaultAcsClient initClient(String accessKeyId,String secret){
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, secret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        return defaultAcsClient;
    }

}
