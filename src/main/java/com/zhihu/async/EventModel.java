package com.zhihu.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/6/24.
 */
//
public class EventModel {
    private EventType type;//Event类型
    private int actorId;//执行的用户id
    private int entityType;//entity的类型
    private int entityId;//entity的id
    private int entityOwnerId;//entity拥有者id

    //用户model的扩展属性字段
    //比如：登录event中要存username和password
    private Map<String, String> exts = new HashMap<String, String>();


    public EventModel() {
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    //setExts
    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    //getExts
    public String getExt(String key) {
        return exts.get(key);
    }

    public Map<String,String> getExts(){return exts;}

    public EventModel setExts(Map<String,String> exts){
        this.exts=exts;
        return this;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }
}
