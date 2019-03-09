package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.Question;
import com.maomaoyu.zhihu.bean.User;
import com.maomaoyu.zhihu.bean.ViewObject;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomayu.zhihu.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * maomaoyu    2018/12/16_15:48
 **/
@Controller
public class HomeController {

    @Reference
    QuestionService questionService;

    @Reference
    UserService userService;

    @Reference
    FollowService followService;

    @Reference
    CommentService commentService;

    @Reference
    HostService hostHandler;

    private List<ViewObject>  getQuestions(int userId,int offset,int limit){
        List<Question> questions = questionService.getLatestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/","index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model,@RequestParam(value = "pop",defaultValue = "0") int pop){
        model.addAttribute("vos",getQuestions(0,0,10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model,@PathVariable("userId") int userId){
        model.addAttribute("vos",getQuestions(userId,0,10));

        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user",user);
        vo.set("commentCount",commentService.getUserCommentCount(userId));
        vo.set("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
        vo.set("followeeCount",followService.getFolloweeCount(userId,EntityType.ENTITY_USER));
        if (hostHandler.getUser() != null){
            vo.set("followed",followService.isFollower(userId,EntityType.ENTITY_USER,hostHandler.getUser().getId()));
        }else {
            vo.set("followed",false);
        }
        model.addAttribute("profileUser",vo);
        return "profile";
    }

}
