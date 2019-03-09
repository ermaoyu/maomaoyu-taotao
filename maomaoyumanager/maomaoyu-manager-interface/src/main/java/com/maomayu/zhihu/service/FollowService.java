package com.maomayu.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * maomaoyu    2018/12/31_13:53
 **/
public interface FollowService {


    /**
     *  用户关注了某个实体,可以关注问题,关注用户,关注评论等任何实体
     *      * @param userId
     *      * @param entityType
     *      * @param entityId
     *      * @return
     * */

    public boolean follow(int userId,int entityType,int entityId);

    /**
     * 取消关注
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean unfollow(int userId,int entityType,int entityId);

    public List<Integer> getFollowers(int entityType,int entityId,int count);

    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count);

    public List<Integer> getFollowees(int userId,int entityType,int count);

    public List<Integer> getFollowees(int userId,int entityType,int offset,int count);

    public long getFollowerCount(int entityType,int entityId);

    public long getFolloweeCount(int userId, int entityType) ;

    public boolean isFollower(int userId,int entityType,int entityId);

}
