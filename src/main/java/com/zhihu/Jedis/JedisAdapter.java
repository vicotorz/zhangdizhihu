package com.zhihu.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhihu.model.User;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Created by dell on 2017/6/18.
 */
public class JedisAdapter {
    public static void print(int i, Object j) {
        System.out.println(String.format("%d:%s", i, j));
    }

    public static void main(String args[]) {
        Jedis jedis = new Jedis("redis://localhost:6379/1");
        jedis.flushDB();

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "NEW");
        print(1, jedis.get("NEW"));
        jedis.setex("hello2", 1800, "2world");

        jedis.set("num", "10");
        jedis.incr("num");
        print(2, jedis.get("num"));
        jedis.incrBy("num", 9);
        print(3, jedis.get("num"));
        jedis.decr("num");
        jedis.decrBy("num", 10);
        print(4, jedis.get("num"));

        String listName = "list";
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "L" + String.valueOf(i));
        }
        print(5, jedis.lrange(listName, 0, 12));
        print(5, jedis.lrange(listName, 0, 3));
        print(5, jedis.llen(listName));
        print(5, jedis.lpop(listName));
        print(5, jedis.lrange(listName, 2, 6));
        print(200, jedis.lindex(listName, 3));//位于位置的元素

        print(5, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "L1", "After-L1"));
        print(5, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "L1", "Before-L1"));
        print(5, jedis.lrange(listName, 0, jedis.llen(listName)));

        String userKey = "MUser";
        jedis.hset(userKey, "name", "Jim");
        jedis.hset(userKey, "age", "18");
        jedis.hset(userKey, "phone", "13131");
        print(6, jedis.hget(userKey, "name"));
        print(6, jedis.hgetAll(userKey));
        //delete
        jedis.hdel(userKey, "phone");
        print(6, jedis.hgetAll(userKey));

        print(6, jedis.hexists(userKey, "email"));
        print(6, jedis.hexists(userKey, "age"));

        print(6, jedis.hkeys(userKey));
        print(6, jedis.hvals(userKey));

        //如果存在，就不添加
        jedis.hsetnx(userKey, "school", "whu");
        jedis.hsetnx(userKey, "name", "zhangdi");
        print(6, jedis.hgetAll(userKey));


        //Set
        String setName1 = "set1";
        String setName2 = "set2";
        for (int i = 0; i < 10; ++i) {
            jedis.sadd(setName1, String.valueOf(i));
            jedis.sadd(setName2, String.valueOf(i * i));
        }
        print(7, jedis.smembers(setName1));
        print(7, jedis.smembers(setName2));
        print(7, jedis.sunion(setName1, setName2));
        print(7, jedis.sdiff(setName1, setName2));//set1独特元素
        print(7, jedis.sinter(setName1, setName2));//交集
        print(7, jedis.sismember(setName1, "12"));//是否在集合中
        print(7, jedis.sismember(setName2, "16"));
        jedis.srem(setName1, "5");//删除
        print(7, jedis.smembers(setName1));
        jedis.smove(setName1, setName2, "25");//移动元素
        print(7, jedis.smembers(setName1));

        print(7, jedis.scard(setName1));//?返回集合的元素个数

        //Rankkey--【sorted set】
        String rankKey = "rank";
        jedis.zadd(rankKey, 15, "Jim");
        jedis.zadd(rankKey, 60, "Ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 75, "Lucy");
        jedis.zadd(rankKey, 80, "Mei");
        print(8, jedis.zcard(rankKey));//?????有序元素个数
        print(8, jedis.zcount(rankKey, 61, 100));//指定区间个数
        print(8, jedis.zscore(rankKey, "Lucy"));//得到score

        jedis.zincrby(rankKey, 200, "Lucy");
        print(8, jedis.zscore(rankKey, "Lucy"));

        print(8, jedis.zrange(rankKey, 0, 100));//前xx名
        print(8, jedis.zrange(rankKey, 0, 10));
        print(8, jedis.zrange(rankKey, 1, 3));//从小到大

        print(8, jedis.zrevrange(rankKey, 1, 3));//从大到小

        //遍历
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
            print(8, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }
        print(8, jedis.zrank(rankKey, "Ben"));//排名
        print(8, jedis.zrevrank(rankKey, "Ben"));

        //优先权set--【sorted set】
        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");

        print(9, jedis.zlexcount(setKey, "-", "+"));
        print(9, jedis.zlexcount(setKey, "(b", "[d"));//count
        print(9, jedis.zlexcount(setKey, "[b", "[d"));
        jedis.zrem(setKey, "b");//删除
        print(9, jedis.zrange(setKey, 0, 10));
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(9, jedis.zrange(setKey, 0, 2));


        //对象序列化
        User user = new User();
        user.setName("xx");
        user.setPassword("ppp");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);

        print(10, JSONObject.toJSONString(user));//JSonObject存储user

        jedis.set("user1", JSONObject.toJSONString(user));
        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);//Json反序列化
        print(10, user2);
        print(10,user2.getSalt());

    }
}
