package com.zhihu.interceptor;


import com.zhihu.dao.TicketDAO;
import com.zhihu.dao.UserDAO;
import com.zhihu.model.HostHolder;
import com.zhihu.model.LoginTicket;
import com.zhihu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by victorz on 2016/7/3.
 * 提交的用户识别
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HostHolder hostHolder;

    //请求前拦截，读取cookie
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {//ticket
                    //找到cookie中ticket字段
                    //System.out.println("找到ticket字段");
                    ticket = cookie.getValue();
                    //System.out.println("ticket值:" + ticket + " getVaule值 " + cookie.getValue());
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.SelectTicket(ticket);
            if (loginTicket == null  || loginTicket.getStatus() != 0) {//|| loginTicket.getExpired().before(new Date())
                //不存在ticket，status==1登录无效
                //没有登录用户
                // 0有效，1无效
                System.out.println(loginTicket == null);
                System.out.println(loginTicket.getExpired().before(new Date()));
                System.out.println(loginTicket.getStatus() != 0);
                //System.out.println("没有登录用户");
                return true;
            }
            //用户登录有效 将用户放入到上下文
            //System.out.println("登录有效，放入上下文");
            User user = userDAO.select(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
