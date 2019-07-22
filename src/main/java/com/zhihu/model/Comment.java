package com.zhihu.model;

import java.util.Date;

/**
 * Created by victorz on 2017/6/10.
 */
public class Comment {
    private int id;//评论id
    private String content;//评论内容
    private int userid;//评论用户id
    private int entityid;//--->questionId  & commentId
    private int entitytype;//question & comment
    private Date createddate;
    private int status;//被删除状态

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
