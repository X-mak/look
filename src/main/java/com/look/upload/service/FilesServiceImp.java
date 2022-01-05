package com.look.upload.service;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FilesServiceImp implements FilesService{

    public String uploadUserImg(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String rootPath = System.getProperty("user.dir")+"/src/main/resources/files/user-img/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootPath);
        return originalFilename;
    }

}
