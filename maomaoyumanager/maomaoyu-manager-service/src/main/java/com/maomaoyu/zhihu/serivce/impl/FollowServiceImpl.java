package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.util.RedisKeyUtil;
import com.maomayu.zhihu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * maomaoyu    2018/12/31_13:53
 **/
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    JedisAdapterImpl jedisAdapterImpl;

    /**
     *  用户关注了某个实体,可以关注问题,关注用户,关注评论等任何实体
     *      * @param userId
     *      * @param entityType
     *      * @param entityId
     *      * @return
     * */

    public boolean follow(int userId,int entityType,int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFollweeKey(userId,entityType);

        Date date = new Date();
        Jedis jedis = jedisAdapterImpl.getJedis();
        Transaction tx = jedisAdapterImpl.multi(jedis);//redis事物
        //实体的粉丝增加当前用户
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));//被关注人粉丝+1
        //当前用户对着类实体关注+1
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));//我的关注+1
        List<Object> ret = jedisAdapterImpl.exec(tx,jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    /**
     * 取消关注
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean unfollow(int userId,int entityType,int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFollweeKey(userId,entityType);

        Date date = new Date();
        Jedis jedis = jedisAdapterImpl.getJedis();
        Transaction tx = jedisAdapterImpl.multi(jedis);//redis事物
        //实体的粉丝减少当前用户
        tx.zrem(followerKey,String.valueOf(userId));//被关注人粉丝-1
        //当前用户对着类实体关注-1
        tx.zrem(followeeKey,String.valueOf(entityId));//我的关注-1
        List<Object> ret = jedisAdapterImpl.exec(tx,jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    private List<Integer> getIdsFromSet(Set<String> idset){
        List<Integer> ids = new ArrayList<>();
        for (String str : idset){
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    public List<Integer> getFollowers(int entityType,int entityId,int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapterImpl.zrevrange(followerKey,0,count));
    }

    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapterImpl.zrevrange(followerKey,offset,offset+count));
    }

    public List<Integer> getFollowees(int userId,int entityType,int count){
        String followeeKey = RedisKeyUtil.getFollweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapterImpl.zrevrange(followeeKey,0,count));
    }

    public List<Integer> getFollowees(int userId,int entityType,int offset,int count){
        String followeeKey = RedisKeyUtil.getFollweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapterImpl.zrevrange(followeeKey,offset,offset+count));
    }

    public long getFollowerCount(int entityType,int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapterImpl.zcard(followerKey);
    }

    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFollweeKey(userId, entityType);
        return jedisAdapterImpl.zcard(followeeKey);
    }

    public boolean isFollower(int userId,int entityType,int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapterImpl.zscore(followerKey,String.valueOf(userId)) != null;
    }

}
