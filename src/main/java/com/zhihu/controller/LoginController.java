package com.zhihu.controller;

import com.zhihu.async.EventModel;
import com.zhihu.async.EventProducer;
import com.zhihu.async.EventType;
import com.zhihu.model.EntityType;
import com.zhihu.model.HostHolder;
import com.zhihu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;


    @RequestMapping(path = {"/toReg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String toReg() {
        return "reg";
    }

    @RequestMapping(path = {"/reg/", "/reg/*"}, method = {RequestMethod.GET, RequestMethod.POST})
//, method = {RequestMethod.POST,RequestMethod.GET}
    public String reg(Model model, @RequestParam(value = "name") String username,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                      HttpServletResponse response) {
//        System.out.println("进入注册方法！");
//        System.out.println("注册方法" + username + "--" + password);
        try {
            Map<String, Object> map = userService.register(username, password);
            //System.out.println("注册完毕");
            if (map.containsKey("ticket")) {
                //System.out.println("注册完找到了ticket票");
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                //System.out.println("注册失败");

                model.addAttribute("msg", map.get("msg"));
                return "reg";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            e.printStackTrace();
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String regloginPage(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        model.addAttribute("msg", "请登录");
        return "login";
    }

    @RequestMapping(path = {"/login", "/login/*"}, method = {RequestMethod.GET, RequestMethod.POST})//RequestMethod
    public String login(Model model, @RequestParam(value = "username", defaultValue = " ") String username,
                        @RequestParam(value = "password", defaultValue = " ") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            if ((" ").equals(username) && (" ").equals(password)) {
                return "login";
            }
            System.out.println(username + "--" + password);
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                //System.out.println("找到ticket票");
                //System.out.println("加入cookie  信息:" + map.get("ticket").toString());
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                   // System.out.println("记住我");
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                //发送邮件
                {
                    EventModel event = new EventModel(EventType.LOGIN);
                    event.setExt("username", username);
                    event.setExt("email", "993610120@qq.com");
                    event.setActorId((int)map.get("userid"));
                    eventProducer.fireEvent(event);
                }
                System.out.println("邮件已经发送，重新跳转中...");

                //System.out.println("重定位");
                return "redirect:/";
            } else {
                //System.out.println("登录失败");
               // System.out.println("错误信息：" + map.get("msg"));
                model.addAttribute("msg", map.get("msg"));
                return "redirect:/";
            }

        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"})//, method = {RequestMethod.GET, RequestMethod.POST}
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        //System.out.println("发生异常！");
        e.printStackTrace();
        return "error" + e.getMessage();
    }
//    @RequestMapping(path={"/error"})
//    @ResponseBody
//    public void Error(Exception e){
//        System.out.println(e.getStackTrace());
//    }

}
