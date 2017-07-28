package com.zhihu.service;

import com.zhihu.Utils.JedisAdapter;
import com.zhihu.Utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 201/6/24.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    //获取赞的的个数
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //返回字符串LIKE:【$!(entityType)】:【$!(entityId)】
        System.out.println();
        return jedisAdapter.scard(likeKey);
    }

    //获取【赞+踩】的状态
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //返回字符串LIKE:【$!(entityType)】:【$!(entityId)】
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        //返回字符串DISLIKE:【$!(entityType)】:【$!(entityId)】
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    //点了赞，消除踩
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //返回字符串LIKE:【$!(entityType)】:【$!(entityId)】
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        //返回字符串DISLIKE:【$!(entityType)】:【$!(entityId)】
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }
    //踩添加，赞消除
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }
}
