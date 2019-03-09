package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.Comment;
import com.maomaoyu.zhihu.util.EntityType;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.CommentService;
import com.maomayu.zhihu.service.HostService;
import com.maomayu.zhihu.service.QuestionService;
import com.maomayu.zhihu.service.SensitiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * maomaoyu    2018/12/23_13:38
 **/
@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Reference
    CommentService commentService;

    @Reference
    HostService hostHandler;

    @Reference
    QuestionService questionService;

    @Reference
    SensitiveService sensitiveService;

//    @Autowired
//    EventProducer eventProducer;

    @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,@RequestParam("content") String content){
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            //过滤敏感词
            Comment comment = new Comment();
            if (hostHandler.getUser() != null ){
                comment.setUserId(hostHandler.getUser().getId());
            }else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);

            commentService.addComment(comment);
            //更新提问里评论的数量
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);//异步化

//            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));
        } catch (Exception e) {
                LOGGER.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }



}
