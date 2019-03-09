package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.Comment;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.CommentService;
import com.maomayu.zhihu.service.HostService;
import com.maomayu.zhihu.service.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * maomaoyu    2018/12/25_20:13
 **/
@Controller
public class LikeController {

    @Reference
    LikeService likeService;

    @Reference
    HostService hostHandler;

    @Reference
    CommentService commentService;

//    @Reference
//    EventProducer eventProducer;

    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }

        Comment comment = commentService.getCommentById(commentId);

//        eventProducer.fireEvent(new EventModel(EventType.LIKE)
//                .setActorId(hostHandler.getUser().getId())
//                .setEntityId(commentId)
//                .setEntityType(EntityType.ENTITY_COMMENT)
//                .setEntityOwnerId(comment.getUserId()).setExt("questionId",String.valueOf(comment.getEntityId())));

        long likeCount = likeService.like(hostHandler.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);

        //System.out.println(likeCount);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if (hostHandler.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.dislike(hostHandler.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
