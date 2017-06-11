package com.zhihu.model;

import java.util.Date;

/**
 * Created by dell on 2017/6/10.
 */
public class Message {
    private int id;
    private int from_id;
    private int to_id;
    private String content;
    private Date created_date;
    private int has_read;
    private String conversation_id;

    public Message() {
    }

    public Message(int id, int from_id, int to_id, String content, Date created_date, int has_read, String conversation_id) {
        this.id = id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.content = content;
        this.created_date = created_date;
        this.has_read = has_read;
        this.conversation_id = conversation_id;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getHas_read() {
        return has_read;
    }

    public void setHas_read(int has_read) {
        this.has_read = has_read;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }
}
