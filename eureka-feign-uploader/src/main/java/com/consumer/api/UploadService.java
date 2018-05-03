package com.consumer.api;

import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jstarseven on 2018/5/2.
 */
@FeignClient(value = "eureka-feign-upload-server", configuration = UploadService.MultipartSupportConfig.class)
public interface UploadService {

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleFileUpload(@RequestPart(value = "file") MultipartFile file);

    @GetMapping("/dc")
    String consumer();

    @Configuration
    class MultipartSupportConfig {
        @Bean
        public SpringFormEncoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}