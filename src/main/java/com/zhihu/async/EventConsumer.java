package com.zhihu.async;

import com.alibaba.fastjson.JSON;
import com.zhihu.Utils.JedisAdapter;
import com.zhihu.Utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/24.
 */
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> config = new HashMap<EventType, List<EventHandler>>();


    @Autowired
    JedisAdapter jedisAdapter;


    //重点理解
    //Spring通过InitializingBean完成一个bean初始化后，对这个bean的回调工作
    //设置完一个bean所有合作者之后
    @Override
    public void afterPropertiesSet() throws Exception {
        //获得Event映射
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);//设置完一个bean的所有合作者之后，对这个bean进行回调
        if(beans!=null){
            for(Map.Entry<String,EventHandler> entry:beans.entrySet()){//获取value
                //这个event所支持的类型
                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();

                //---从上下文中获取
                for(EventType type:eventTypes) {//获取type
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }


        //开启处理线程
        //启动处理线程
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key= RedisKeyUtil.getEventQueueKey();
                    List<String> events=jedisAdapter.brpop(0,key);
                    for(String message: events){
                        //如果有则跳过
                        if(message.equals(key)){
                            continue;
                        }
                        //没有事件表示，存入
                        //识别该由哪个handler处理
                        EventModel eventModel = JSON.parseObject(message,EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别事件");
                            continue;
                        }
                        //获取这个事件的handler，进行事件的处理
                        for(EventHandler handler:config.get(eventModel.getType())){
                            handler.doHandle(eventModel);
                        }
                    }
                }

            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }


}
