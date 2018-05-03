package com.consumer.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title: UploadController
 * Description: file access
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author lingrui
 * @version 1.0
 * @date 2018/5/2
 */
@RestController
public class UploadController {

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
        System.out.println(file.getName());
        return file.getName();
    }

}