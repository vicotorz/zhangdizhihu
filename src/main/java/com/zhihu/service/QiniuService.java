package com.zhihu.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by dell on 2017/7/22.
 */
@Service
public class QiniuService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QiniuService.class);

    //1.设置七牛keys
    //9IqzYh5tLrQQPKWPRBJDx0EXU6RKJaKT7_TjdvP4
    //hTTY8asdFdvgqte9LatLUlUZhh7dcHe3p1LEW6Kr
    String  ACCESS_KEY="9IqzYh5tLrQQPKWPRBJDx0EXU6RKJaKT7_TjdvP4";
    String  SECRET_KEY="hTTY8asdFdvgqte9LatLUlUZhh7dcHe3p1LEW6Kr";

    //2.上传的空间
    String bucketname="wenda";
    //3.密钥配置
    Auth auth=Auth.create(ACCESS_KEY,SECRET_KEY);
    //4.上传对象
    UploadManager uploadManager=new UploadManager();

    //5.七牛域名
    private static String QINIU_IMAGE_DOMAIN = "oti92rltw.bkt.clouddn.com";

    //6.简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    //7.上传七牛云
    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ImageService.isFileAllowed(fileExt)) {
                return null;
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            //上传文件二进制码getByte，文件名
            String token=getUpToken();
            System.out.println(token);
            Response res = uploadManager.put(file.getBytes(), fileName, token);
            System.out.println(res.toString());
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
                //response返回的是json，JSONObject.parseObject解析json，并获得key
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
