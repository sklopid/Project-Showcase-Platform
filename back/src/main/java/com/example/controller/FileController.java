package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import com.example.exception.CustomerException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 处理文件上传下载相关的接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * 文件上传
     */
    @PostMapping("upload")
    public Result upload(@RequestParam("file")MultipartFile file) throws Exception{
        //找到文件的位置
        String filePath = System.getProperty("user.dir")+"/files/"; //获取到当前项目的根路径D:/demo+/files/
        if (!FileUtil.isDirectory(filePath))
        {
            FileUtil.mkdir(filePath);
        }
        byte[] bytes = file.getBytes();
        String fileName = System.currentTimeMillis()+"_"+ file.getOriginalFilename();//文件的原始名称
        String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        //写入文件
        FileUtil.writeBytes(bytes,filePath+encodeFileName);
        String url = "http://localhost:9999/files/download/"+fileName;
        return Result.success(url);
    }
    /**
     * 文件下载
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception{
        //找到文件的位置
        String filePath = System.getProperty("user.dir")+"/files/"; //获取到当前项目的根路径D:\demo
        String realPath =  filePath + fileName;//D:\demo/files/login.png
        boolean exist =  FileUtil.exist(realPath);
        if(!exist){
            throw new CustomerException("no file");
        }
        //读取文件的字节流
        byte[] bytes = FileUtil.readBytes(realPath);
        ServletOutputStream os = response.getOutputStream();
        //输出流对象把文件写出到客户端
        os.write(bytes);
        os.flush();
        os.close();

    }
}
