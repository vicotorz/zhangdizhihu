package com.zhihu.async;

import com.alibaba.fastjson.JSONObject;

import com.zhihu.Utils.JedisAdapter;
import com.zhihu.Utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dell on 2017/6/24.
 */
public class EventProducer {
    //使用Jedis压入到队列

    @Autowired
    JedisAdapter jedisAdapter;
    public boolean fireEvent(EventModel eventModel){
        //序列化，入队
        String json= JSONObject.toJSONString(eventModel);
        String key= RedisKeyUtil.getEventQueueKey();//返回BIZ_EVENTQUEUE;
        jedisAdapter.lpush(key,json);//加入的内容：BIZ_EVENTQUEUE --------- 对象序列化
        return true;
    }
}