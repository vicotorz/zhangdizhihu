package com.zhihu.model;

import java.util.Date;

/**
 * Created by dell on 2017/6/10.
 */
public class Comment {
    private int id;
    private String content;
    private int userid;
    private int entityid;
    private int entitytype;
    private Date createddate;
    private int status;

    public Comment() {
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEntityid() {
        return entityid;
    }

    public void setEntityid(int entityid) {
        this.entityid = entityid;
    }

    public int getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(int entitytype) {
        this.entitytype = entitytype;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
