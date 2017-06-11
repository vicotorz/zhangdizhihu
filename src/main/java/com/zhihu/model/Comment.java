package com.zhihu.model;

import java.util.Date;

/**
 * Created by dell on 2017/6/10.
 */
public class Comment {
    private int id;
    private String content;
    private int user_id;
    private int entity_id;
    private int entity_type;
    private Date created_date;
    private int status;

    public Comment() {
    }

    public Comment(int id, String content, int user_id, int entity_id, int entity_type, Date created_date, int status) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.entity_id = entity_id;
        this.entity_type = entity_type;
        this.created_date = created_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }

    public int getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(int entity_type) {
        this.entity_type = entity_type;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
