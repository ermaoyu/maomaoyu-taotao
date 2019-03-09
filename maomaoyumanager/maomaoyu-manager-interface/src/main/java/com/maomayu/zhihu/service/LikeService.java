package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.util.RedisKeyUtil;

/**
 * maomaoyu    2018/12/25_20:05
 **/
public interface LikeService {


    public long getLikeCount(int entityType,int entityId);

    public int getLikeStatus(int userId,int entityType,int entityId);

    public long like(int userId,int entityType,int entityId);

    public long dislike(int userId,int entityType,int entityId);
}
