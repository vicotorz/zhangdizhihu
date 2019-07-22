package com.zhihu.async;

/**
 * Created by victorz on 2017/6/24.
 */
//定义队列EventType
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    ///////////////关注事件
    FOLLOW(4),
    UNFOLLOW(5),
    ADD_QUESTION(6);

    private int value;
    EventType(int value){this.value=value;}
    public int getValue(){return value;}
}
