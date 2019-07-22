package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.async.EventModel;
import com.zhihu.async.EventProducer;
import com.zhihu.async.EventType;
import com.zhihu.model.HostHolder;
import com.zhihu.model.User;
import com.zhihu.service.ImageService;
import com.zhihu.service.QiniuService;
import com.zhihu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * Created by victorz on 2016/7/2.
 */
@Controller
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    ImageService imageService;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    //展示图片
    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            //设置相应类型
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new
                    File("D:/upload/" + imageName)), response.getOutputStream());
        } catch (Exception e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadGet() {
        System.out.println("先以get的请求方式显示上传页面");
        return "upload";
    }

    //上传图片
    @RequestMapping(path = {"/uploadImage","/uploadImage/"}, method = {RequestMethod.POST})
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            //String fileUrl = imageService.saveImage(file);
            System.out.println("上传图片");
            String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return JsonUtil.getJSONString(1, "上传图片失败");
            } else {
                User user;
                if (hostHolder.getUser()!= null) {
                    //更新头像连接
                    user = hostHolder.getUser();
                    String f=fileUrl.replace("oti92rltw.bkt.clouddn.com","");
                    String headUrl="http://oti92rltw.bkt.clouddn.com/"+f;
                    user.setHeadUrl(headUrl);
                    userService.editImage(user);
                } else {
                    return JsonUtil.getJSONString(1, "请登录");
                }
            }
            return "index";
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            e.printStackTrace();
            return JsonUtil.getJSONString(1, "上传失败");
        }
    }
}
