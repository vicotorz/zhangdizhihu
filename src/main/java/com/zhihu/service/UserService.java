package com.zhihu.service;

import com.zhihu.dao.UserDAO;
import com.zhihu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2017/5/23.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public User getUser(int userId) {
        return userDAO.select(userId);
    }

}
