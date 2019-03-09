package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.Feed;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomaoyu.zhihu.util.RedisKeyUtil;
import com.maomayu.zhihu.service.FeedService;
import com.maomayu.zhihu.service.FollowService;
import com.maomayu.zhihu.service.HostService;
import com.maomayu.zhihu.service.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * maomaoyu    2019/1/5_15:30
 **/
@Controller
public class FeedController {
    @Reference
    FeedService feedService;

    @Reference
    HostService hostHandler;

    @Reference
    FollowService followService;

    @Reference
    JedisAdapter jedisAdapter;

    /**
     *  推模式
     *  用户访问时从数据库拉取数据
     * */
    @RequestMapping(path = {"/pullfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String getPullFeeds(Model model){
        int localUserId = hostHandler.getUser() != null ? hostHandler.getUser().getId() : 0 ;
        List<Integer> followees = new ArrayList<>();//关注用户的Id
        if (localUserId != 0){
            //关注的人
            followees = followService.getFollowees(localUserId,EntityType.ENTITY_USER,Integer.MAX_VALUE);
            System.out.println(followees);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE ,followees,10);
        System.out.println(feeds);
        model.addAttribute("feeds" ,feeds);
        return "feeds";
    }


    /**
     *  拉模式
     *  生成timeLine内容,动态,读取压力小,
     * */
    @RequestMapping(path = {"/pushfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String getPushFeeds(Model model){
        int localUserId = hostHandler.getUser() != null ? hostHandler.getUser().getId() : 0;
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId),0,10);//先从缓存中拿取最新的timeLine
        List<Feed> feeds = new ArrayList<>();
        for (String feedId : feedIds){
            Feed feed = feedService.getById(Integer.parseInt(feedId));
            if (feed != null){
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds" ,feeds);
        return "feeds";
    }
}
