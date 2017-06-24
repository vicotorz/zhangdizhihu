package com.zhihu.async;

/**
 * Created by dell on 2017/6/24.
 */
//定义队列EventType
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    EventType(int value){this.value=value;}
    public int getValue(){return value;}
}
