package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.bean.Feed;
import com.maomayu.zhihu.service.FeedService;
import com.maomaoyu.zhihu.mapper.FeedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.List;

/**
 * maomaoyu    2019/1/5_14:45
 **/
@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    FeedMapper feedMapper;

    public List<Feed> getUserFeeds(int maxId,List<Integer> userIds,int count){
        return feedMapper.selectUserFeeds(maxId,userIds,count);
    }

    public boolean addaFeed(Feed feed){
        feedMapper.addFeed(feed) ;
        return feed.getId() > 0;
    }

    public Feed getById(int id){
        return feedMapper.getFeedById(id);
    }
}
