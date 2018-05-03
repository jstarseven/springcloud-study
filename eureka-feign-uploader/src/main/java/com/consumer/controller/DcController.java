package com.consumer.controller;

import com.consumer.api.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    @Autowired
    UploadService uploadService;

    @GetMapping("/consumer")
    public String dc() {
        return uploadService.consumer();
    }

}