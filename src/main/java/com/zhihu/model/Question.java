package com.zhihu.model;

import java.util.Date;

/**
 * Created by victorz on 2017/5/23.
 */
public class Question {
    private int id;
    private  String title;
    private String content;
    private Date createdDate;
    private int userId;
    private int commentCount;

    public Question(){

    }

    public Question(String title, String content, Date createdDate, int userId, int commentCount) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.userId = userId;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
