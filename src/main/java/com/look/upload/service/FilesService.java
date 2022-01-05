package com.look.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {

    public String uploadUserImg(MultipartFile file) throws IOException;
}
