package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.*;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * maomaoyu    2018/12/22_18:49
 **/
@Controller
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Reference
    CommentService commentService;

    @Reference
    QuestionService questionService;

    @Reference
    HostService hostHandler;

    @Reference
    UserService userService;

    @Reference
    FollowService followService;

    @Reference
    LikeService likeService;

    @RequestMapping(value = {"/question/{qid}"},method = {RequestMethod.GET})
    public String questionDetail(Model model,@PathVariable("qid") int qid){
        Question question = questionService.getById(qid);
        model.addAttribute("question",question);
        List<Comment> commentList = commentService.getCommentsByEntity(qid,EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment  : commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            if (hostHandler.getUser() == null){
                vo.set("liked",0);
            }else {
                vo.set("liked",likeService.getLikeStatus(hostHandler.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));

            }
            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user",userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments",vos);
        List<ViewObject> followUsers = new ArrayList<>();
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION,qid,20);
        //获取关注的用户信息
        for (Integer userId : users){
            ViewObject vo = new ViewObject();
            User u = userService.getUser(userId);
            if (u == null){
                continue;
            }
            vo.set("name",u.getName());
            vo.set("headUrl",u.getHeadUrl());
            vo.set("id",u.getId());
            followUsers.add(vo);
        }

        model.addAttribute("followUsers",followUsers);
        if (hostHandler.getUser() != null){
            model.addAttribute("followed",followService.isFollower(hostHandler.getUser().getId(),EntityType.ENTITY_QUESTION,qid));
        }else {
            model.addAttribute("followed",false);
        }

        return "detail";
    }

    @RequestMapping(path = {"/question/add"} ,method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam String title,@RequestParam String content){
        try {
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            if (hostHandler.getUser() == null){
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
                return WendaUtil.getJSONString(999);
            }else {
                question.setUserId(hostHandler.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0){
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            LOGGER.error("增加失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");
    }
}
