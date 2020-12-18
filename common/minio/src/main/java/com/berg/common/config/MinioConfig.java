package com.berg.common.config;

import com.berg.common.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ConditionalOnProperty(prefix = "minio", name ="url")
@Configuration
public class MinioConfig {

    @Value(value = "${minio.url}")
    String url;
    @Value(value = "${minio.serviceUrl}")
    String serviceUrl;
    @Value(value = "${minio.name}")
    String name;
    @Value(value = "${minio.password}")
    String password;
    @Value(value = "${minio.bucketName}")
    String bucketName;

    @Bean
    public void initMinio(){
        if(!url.startsWith("http")){
            url = "http://" + url;
        }
        if(!url.endsWith("/")){
            url = url.concat("/");
        }
        MinioUtil.setMinioUrl(url);
        MinioUtil.setMinioName(name);
        MinioUtil.setMinioPass(password);

        //创建bucket
        try{
            MinioUtil.initMinio();
        }catch (Exception ex){
            log.error("初始化创建MinioClient失败："+ex.getMessage());
        }
    }

}
