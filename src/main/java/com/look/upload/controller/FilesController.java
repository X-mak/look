package com.look.upload.controller;

import com.look.common.Result;
import com.look.upload.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FilesController {

    @Autowired
    FilesService filesService;

    @PostMapping("/user")
    public Result<?> upload(MultipartFile file) {
        try{
            String name = filesService.uploadUserImg(file);
            return Result.success(name,"上传成功!");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("400","上传失败!");
        }
    }
}
