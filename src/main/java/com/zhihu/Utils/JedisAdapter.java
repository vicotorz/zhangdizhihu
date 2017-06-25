package com.zhihu.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by dell on 2017/6/24.
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger= LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;
    private Jedis jedis;
    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/2");
    }

    //Set添加
    public long sadd(String key,String value){
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch(Exception e){
            logger.error("Jedis--sadd方法异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
       return 0;
    }

    //Set删除
    public long srem(String key,String value){
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("Jedis--srem方法异常"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    //Set弹出
    public long scard(String key){
        try{
            jedis=pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("Jedis--scard方法异常"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    //Set是否是一个成员
    public boolean sismember(String key,String value){
        try{
            jedis=pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("Jedis--sismember异常"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return false;
    }

    //弹出
    public List<String> brpop(int timeout, String key){
        try{
            jedis=pool.getResource();
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("Jedis--brpop异常"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    //List添加
    public long lpush(String key,String value){
        try{
            jedis=pool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("Jedis--lpush异常"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
}
