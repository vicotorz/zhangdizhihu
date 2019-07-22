package com.zhihu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Created by dell on 2017/5/23.
 */
@Service
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    public static String[] IMAGE_FILE_EXTD = new String[] {"png", "bmp", "jpg", "jpeg"};
    private final String file_Path="D:/upload/";
    //存入图片
    public String saveImage(MultipartFile file) throws IOException{
        //xxx.jpg  获取文件名
        int dotPosition=file.getOriginalFilename().lastIndexOf(".");
        if(dotPosition<0){
            return null;
        }
        //获得后缀名
        String fileExt=file.getOriginalFilename().substring(dotPosition+1).toLowerCase();
        if(isFileAllowed(fileExt)){
            return null;
        }

        String fileName= UUID.randomUUID().
                toString().replaceAll("-","")+"."+fileExt;

        //存入图片--存入策略
        Files.copy(file.getInputStream(),
                new File(file_Path+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return "http://127.0.0.1:8080/image?name="+ fileName;
    }

    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
