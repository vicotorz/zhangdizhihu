package com.zhihu.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by victorz on 2017/6/24.
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/7");
        logger.info("初始化jedispool");
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    //阻塞队列弹出元素
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        logger.info("brpop方法调用");
        try {
            logger.info("获得连接");
            jedis = pool.getResource();
            logger.info("获得连接，brpop");
            logger.info(timeout+"--?--"+key);
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("Jedis--brpop异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    //Set添加
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("Jedis--sadd方法异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    //Set删除
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("Jedis--srem方法异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    //Set弹出
    public long scard(String key) {
        Jedis jedis = null;
        logger.info("scard");
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("Jedis--scard方法异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    //Set是否是一个成员
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        logger.info("sismember方法调用");
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("Jedis--sismember异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    //List添加
    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            logger.info(key + "---" + value);
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("Jedis--lpush异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    ////////增加jedis事务操作
    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {

        }
        return null;
    }
    //Transcation
    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
           return tx.exec();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                try {
                    tx.close();
                } catch (IOException ioe) {
                }
            }

            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    //从小到大排名
    public Set<String> zrange(String key, int start, int end){
        Jedis jedis=null;
        try{
                 jedis=pool.getResource();
                 return jedis.zrange(key,start,end);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
    //从大到小排名
    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zrevrange(key,start,end);

        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

     //有序元素个数
    public long zcard(String key){
        Jedis jedis=null;
        try{
         jedis=pool.getResource();
         return jedis.zcard(key);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    //得打score
    public Double zscore(String key, String number){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zscore(key,number);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
