package com.zhihu.controller;

import com.zhihu.dao.UserDAO;
import com.zhihu.model.User;
import com.zhihu.service.wendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by victorz on 2017/5/15.
 * 项目测试页
 */
//@Controller
public class IndexController {
    @Autowired
    wendaService mservice;

    @Autowired
    //@Qualifier("DAO")
    UserDAO userDAO;

    @RequestMapping({"/init"})
    @ResponseBody
    public void initDatabase() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d,", i));
            user.setPassword("");
            user.setSalt("");
            //System.out.println("存入数据库");
            userDAO.addUser(user);
            //System.out.println("存完数据库");
        }
    }

    @RequestMapping({"/ppp"})
    @ResponseBody
    public String injection() {
        return mservice.sayHello();
    }

    @RequestMapping({"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello Controller";
    }

    @RequestMapping(path = {"/profile/{userID}"})
    @ResponseBody
    public String profile(@PathVariable("userID") String id, @RequestParam(value = "key", defaultValue = "defaultVaule") String key, @RequestParam(value = "type", defaultValue = "defaultVaule") String type) {
        return "The page of" + id + " key:" + key + " value:" + type;
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        //cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie:" + cookie.getName() + "Cookie value:" + cookie.getValue());
            }
        }
        return sb.toString();
    }

    @RequestMapping(path = "/watch")
    @ResponseBody
    public String watch_cookie(@CookieValue("JSESSIONID") String id, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        sb.append(id + "<br>");
        response.addHeader("nowcoder", "hello");
        return sb.toString();
    }

    @RequestMapping(path = {"/vm"})
    public String template(Model model) {
        System.out.println("templates方法");
        model.addAttribute("value1", "无影风magic");

        //传list
        List<String> list = Arrays.asList(new String[]{"Red", "Orange"});
        model.addAttribute("colors", list);

        //传入map
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 1; i <= 3; i++) {
            map.put(i, i * i);
        }
        model.addAttribute("map", map);

        model.addAttribute("user", new User("magic", "123456"));
        return "Home";//返回Home的模版
    }

    @RequestMapping(path = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code) {
        return "redirect:/";
    }

    @RequestMapping(path = "/red/{code}")
    public RedirectView red(@PathVariable("code") int code, HttpSession httpSession) {
        RedirectView redirectView = new RedirectView("/", true);
        httpSession.setAttribute("msg", "jump from red function");
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;
    }

    @RequestMapping(path = "/admin", method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        } else {
            throw new IllegalArgumentException("不是admin，发生故障！");
        }
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error" + e.getMessage();
    }
}
