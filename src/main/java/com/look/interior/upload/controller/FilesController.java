package com.look.interior.upload.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.look.common.Result;
import com.look.interior.upload.service.FilesService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    FilesService filesService;

    @PostMapping("/{type}")
    public Result<?> upload(MultipartFile file, @PathVariable String type) {
        try{
            //type决定上传的文件类型
            //user-img：用户头像上传
            //video：课程视频上传
            //video-img：课程封面上传
            String name = filesService.uploadUserImg(file,type);
            name =  "http://localhost:8080/files/" + type + "/" + name;
            return Result.success(name,"上传成功!");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("400","上传失败!");
        }
    }


    @GetMapping("/{type}/{flag}")
    public void download(@PathVariable String type, @PathVariable String flag, HttpServletResponse response, HttpServletRequest request){
        //type决定下载的文件类型
        //user-img：用户头像上传
        //video：课程视频上传
        //video-img：课程封面上传
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/"+type+"/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        if(! fileNames.contains(flag))return ;
        try{
            if(StrUtil.isNotEmpty(flag)){
                response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(flag,"UTF-8"));
                response.setContentType("application/octet-stream");
//                response.setContentType("video/webm");
                byte[] bytes = FileUtil.readBytes(basePath+flag);
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Content-Length", String.valueOf(bytes.length));
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();

                os.close();
            }
        }catch (Exception e){
            System.out.println("下载失败!");
        }
    }

}
