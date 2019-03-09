package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.Feed;

import java.util.List;

/**
 * maomaoyu    2019/1/5_14:45
 **/
public interface FeedService {

    public List<Feed> getUserFeeds(int maxId,List<Integer> userIds,int count);

    public boolean addaFeed(Feed feed);

    public Feed getById(int id);
}
