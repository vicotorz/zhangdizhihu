package com.zhihu.service;

import com.zhihu.Utils.Md5Util;
import com.zhihu.dao.TicketDAO;
import com.zhihu.dao.UserDAO;
import com.zhihu.model.LoginTicket;
import com.zhihu.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by victorz on 2017/5/23.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketDAO ticketDao;


    public User getUser(int userId) {
        return userDAO.select(userId);
    }

    public User getUserByName(String name) {
        return userDAO.selectByName(name);
    }

    private String addLoginTicket(int userId) {
        //不能对统一个用户发重复ticket票
        LoginTicket ticket=ticketDao.SelectTicketByid(userId);
        if(ticket!=null){
            return ticket.getTicket();
        }else{
        ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        ticketDao.addTicket(ticket);
        return ticket.getTicket();
        }
    }

    //验证用户功能
    public HashMap login(String name, String password) {
        HashMap<String, Object> map = new HashMap<>();
        if (name.equals(" ") && password.equals(" ")) {
            return map;
        } else {
            User user = userDAO.selectByName(name);
            System.out.println(user.getSalt());
            System.out.println(password);
            System.out.println(Md5Util.MD5(password + user.getSalt()));
            System.out.println(user.getPassword());
            System.out.println(user.getPassword().equals(Md5Util.MD5(password + user.getSalt())));
            if (user == null) {
                map.put("msg", "用户名错误");
            } else if (!user.getPassword().equals(Md5Util.MD5(password + user.getSalt()))) {
                map.put("msg", "密码错误");
            } else {
                //登录成功！发ticket票
                System.out.println("登录成功");
                String ticket = addLoginTicket(user.getId());
                map.put("ticket", ticket);
                map.put("userid", user.getId());
            }
            return map;
        }
    }

    //注册用户功能
    public HashMap register(String name, String password) {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
        } else if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
        } else if (userDAO.selectByName(name)!=null) {
            map.put("msg", "该用户已经被注册");
        } else {
            //注册
            User user = new User();
            user.setName(name);
            String salt = UUID.randomUUID().toString().substring(1, 5);
            user.setSalt(salt);
            user.setPassword(Md5Util.MD5(password + salt));
            Random random = new Random();
            user.setHeadUrl(String.format("http://images.newcoder.com/head/%dt.png", random.nextInt(1000)));

            userDAO.addUser(user);

            //分配ticket票
            String ticket = addLoginTicket(user.getId());
            map.put("ticket", ticket);

        }
        return map;
    }

    //更新用户头像
    public void editImage(User user){
        userDAO.editImage(user);
    }

    //登出清空ticket
    public void logout(String ticket) {
        ticketDao.updateticket(ticket, 1);
    }

}
