package org.example.controller;

import org.example.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {

        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();

        //保证文件的名字是唯一的，防止文件覆盖
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("D:\\spring-boot-2025\\spring-boot-2025\\files\\" + filename));
        return Result.success("url访问地址");
    }
}
