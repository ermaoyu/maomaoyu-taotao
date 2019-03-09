package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.util.RedisKeyUtil;
import com.maomayu.zhihu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * maomaoyu    2018/12/25_20:05
 **/
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    JedisAdapterImpl jedisAdapterImpl;

    public long getLikeCount(int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapterImpl.scard(likeKey);
    }

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if (jedisAdapterImpl.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        return jedisAdapterImpl.sismember(disLikeKey,String.valueOf(userId)) ? 1 : 0;
    }

    public long like(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapterImpl.sadd(likeKey,String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapterImpl.srem(disLikeKey,String.valueOf(userId));
        return jedisAdapterImpl.scard(likeKey);
    }

    public long dislike(int userId,int entityType,int entityId){
        String disLikeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapterImpl.sadd(disLikeKey,String.valueOf(userId));

        String likeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapterImpl.srem(likeKey,String.valueOf(userId));
        return jedisAdapterImpl.scard(likeKey);
    }
}
