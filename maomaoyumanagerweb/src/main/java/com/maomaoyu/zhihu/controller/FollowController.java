package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.*;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * maomaoyu    2018/12/31_14:13
 **/
@Controller
public class FollowController {

    @Reference
    FollowService followService;

    @Reference
    HostService hostHandler;

//    @Reference
//    EventProducer eventProducer;

    @Reference
    UserService userService;

    @Reference
    CommentService commentService;

    @Reference
    QuestionService questionService;

    @RequestMapping(path = {"/followUser"},method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }

        boolean ret = followService.follow(hostHandler.getUser().getId(),EntityType.ENTITY_USER,userId);
//        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHandler.getUser().getId()).setEntityOwnerId(userId).setEntityId(userId).setEntityType(EntityType.ENTITY_USER));
        //返回关注人数
        return WendaUtil.getJSONString(ret ? 0 : 1,String.valueOf(followService.getFolloweeCount(hostHandler.getUser().getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }

        boolean ret = followService.unfollow(hostHandler.getUser().getId(),EntityType.ENTITY_USER,userId);
//        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(hostHandler.getUser().getId()).setEntityOwnerId(userId).setEntityId(userId).setEntityType(EntityType.ENTITY_USER));

        //返回关注人数
        return WendaUtil.getJSONString(ret ? 0 : 1,String.valueOf(followService.getFolloweeCount(hostHandler.getUser().getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/followQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Question q = questionService.getById(questionId);
        if (q == null){
            return WendaUtil.getJSONString(1,"问题不存在");
        }

//        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHandler.getUser().getId())
//                .setEntityOwnerId(q.getUserId()).setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION));


        boolean ret = followService.follow(hostHandler.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);
        Map<String,Object> info = new HashMap<>();
        info.put("headUrl",hostHandler.getUser().getHeadUrl());
        info.put("name",hostHandler.getUser().getName());
        info.put("id",hostHandler.getUser().getId());
        info.put("count",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        return WendaUtil.getJSONString(ret ? 0 : 1,info);
    }

    @RequestMapping(path = {"/unfollowQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Question q = questionService.getById(questionId);
        if (q == null){
            return WendaUtil.getJSONString(1,"问题不存在");
        }

        boolean ret = followService.unfollow(hostHandler.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);

//        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(hostHandler.getUser().getId())
//                .setEntityOwnerId(q.getUserId()).setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION));


        Map<String,Object> info = new HashMap<>();
        info.put("id",hostHandler.getUser().getId());
        info.put("count",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        return WendaUtil.getJSONString(ret ? 0 : 1,info);
    }

    @RequestMapping(path = {"/user/{uid}/followers"},method = {RequestMethod.GET})
    public String followers(Model model,@PathVariable("uid") int userId ){
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER,userId,0,10);
        if (hostHandler.getUser() != null){
            model.addAttribute("followers",getUserInfo(hostHandler.getUser().getId(),followerIds));
        }else {
            model.addAttribute("followers",getUserInfo(0,followerIds));
        }
        model.addAttribute("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
        model.addAttribute("curUser",userService.getUser(userId));
        return "followers";
    }

    @RequestMapping(path = {"/user/{uid}/followees"},method = {RequestMethod.GET})
    public String followees(Model model,@PathVariable("uid") int userId ){
        List<Integer> followeeIds = followService.getFollowees(userId,EntityType.ENTITY_USER,0,10);
        if (hostHandler.getUser() != null){
            model.addAttribute("followees",getUserInfo(hostHandler.getUser().getId(),followeeIds));
        }else {
            model.addAttribute("followees",getUserInfo(0,followeeIds));
        }
        model.addAttribute("followeeCount",followService.getFolloweeCount(userId,EntityType.ENTITY_USER));
        model.addAttribute("curUser",userService.getUser(userId));
        return "followees";
    }

    private List<ViewObject> getUserInfo(int localUserId,List<Integer> userIds){
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIds){
            User user = userService.getUser(uid);
            if (user == null){
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user",user);
            vo.set("commentCount",commentService.getUserCommentCount(uid));
            vo.set("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,uid));
            vo.set("followeeCount",followService.getFolloweeCount(uid,EntityType.ENTITY_USER));
            if (localUserId != 0){
                vo.set("followed",followService.isFollower(localUserId,EntityType.ENTITY_USER,uid));
            }else {
                vo.set("followed",false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }

}
