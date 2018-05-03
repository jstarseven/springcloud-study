package com.consumer.service;

import com.consumer.api.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Title: UploadTester
 * Description: file uploader
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author lingrui
 * @version 1.0
 * @date 2018/5/2
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UploadTester {

    @Autowired
    private UploadService uploadService;

    @Test
    public void testHandleFileUpload() {

        File file = new File("readme.md");
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file", MediaType.TEXT_PLAIN_VALUE, true, file.getName());
        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        MultipartFile multi = new CommonsMultipartFile(fileItem);
        System.out.println(uploadService.handleFileUpload(multi));
    }

}