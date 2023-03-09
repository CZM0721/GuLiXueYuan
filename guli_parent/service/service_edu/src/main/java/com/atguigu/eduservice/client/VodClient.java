package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-vdo")
public interface VodClient {

    @DeleteMapping("/eduvod/video/delAliyunVod/{id}")
    R delAliyunVod(@PathVariable("id") String id);

}
