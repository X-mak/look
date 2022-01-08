package com.look.interior.upload.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FilesServiceImp implements FilesService{

    public String uploadUserImg(MultipartFile file,String type) throws IOException {
        String originalFilename = IdUtil.fastSimpleUUID()+"_"+file.getOriginalFilename();
        String rootPath = System.getProperty("user.dir")+"/src/main/resources/files/"+type+"/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootPath);
        return originalFilename;
    }


}
