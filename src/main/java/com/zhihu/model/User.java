package com.zhihu.model;

import org.springframework.stereotype.Component;

/**
 * Created by dell on 2017/5/16.
 */
@Component
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
    public User(){}

    public User(int id, String name, String password, String salt, String headUrl) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.headUrl = headUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public String getSalt() {
        return salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
