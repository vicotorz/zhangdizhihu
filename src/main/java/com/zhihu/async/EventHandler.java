package com.zhihu.async;

import java.util.List;

/**
 * Created by dell on 2017/6/24.
 */
public interface EventHandler {
    //处理handler方法
    void doHandle(EventModel model);

    //获得支持的EventType类型
    List<EventType> getSupportEventTypes();
}