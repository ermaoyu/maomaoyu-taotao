package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.bean.Message;
import com.maomaoyu.zhihu.bean.User;
import com.maomaoyu.zhihu.bean.ViewObject;
import com.maomaoyu.zhihu.util.PageBean;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.HostService;
import com.maomayu.zhihu.service.MessageService;
import com.maomayu.zhihu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * maomaoyu    2018/12/23_14:52
 **/
@Controller
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Reference
    MessageService messageService;

    @Reference
    UserService userService;

    @Reference
    HostService hostHandler;

//    @Reference
//    EventProducer eventProducer;

    @RequestMapping(path = {"/msg/list"},method = {RequestMethod.GET})
    public String conversationList(Model model,@RequestParam(value = "page",defaultValue = "1") int page
            ,HttpServletRequest request){
        try {
            int localUserId = hostHandler.getUser().getId();
            //System.out.println(localUserId);
            //分页
            PageBean<ViewObject> pageBeanList = (PageBean<ViewObject>) request.getAttribute("pageBeanList");
            if (pageBeanList == null){
                pageBeanList = new PageBean<ViewObject>();
                pageBeanList.setTotalCount(messageService.getConversationCount(localUserId));//总条数
                pageBeanList.setId(localUserId);
            }
            if (page > 0 && page <= pageBeanList.getPageCount()){
                pageBeanList.setCurPage(page);
            }
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId,pageBeanList.getCurPage(),5);
            for (Message msg : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                //System.out.println(targetId);
                User user = userService.getUser(targetId);
                vo.set("user",user);
                vo.set("unread",messageService.getConvesationUnreadCount(localUserId,msg.getConversationId()));
                conversations.add(vo);
            }
            pageBeanList.setPageDate(conversations);
            model.addAttribute("pageBeanList",pageBeanList);
            model.addAttribute("conversations",conversations);
        } catch (Exception e) {
            LOGGER.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"},method = {RequestMethod.GET})
    public String conversationDetail(Model model,@RequestParam("conversationId") String conversationId,@RequestParam(value = "page",defaultValue = "1") int page, HttpServletRequest request){
        try {
            List<ViewObject> messages = new ArrayList<>();
            //分页
            //异步已读信息

            PageBean<ViewObject> pageBeanDetail = (PageBean<ViewObject>) request.getAttribute("pageBeanDetail");
            if (pageBeanDetail == null){
                pageBeanDetail = new PageBean<ViewObject>();
                pageBeanDetail.setTotalCount(messageService.getConversationDetailCount(conversationId));
                pageBeanDetail.setStrId(conversationId);//保存该用户与其他用户对话的conversationId
            }

            if (page > 0 && page <= pageBeanDetail.getPageCount()){
                pageBeanDetail.setCurPage(page);
            }
            List<Message> conversationList = messageService.getConversationDetail(conversationId,pageBeanDetail.getCurPage(),10);
            for (Message msg : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null){
                    continue;
                }
//                eventProducer.fireEvent(new EventModel(EventType.ReadMessage).setActorId(msg.getId()));
//                vo.set("headUrl",user.getHeadUrl());
//                vo.set("userId", user.getId());
                messages.add(vo);
            }
            pageBeanDetail.setPageDate(messages);
            model.addAttribute("pageBeanDetail",pageBeanDetail);
            model.addAttribute("messages",messages);
        } catch (Exception e) {
            LOGGER.error("获取站内信列表失败" + e.getMessage());
        }

        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content")String content){
        try {
            if (hostHandler.getUser() == null){
                return WendaUtil.getJSONString(999,"未登录");
            }
            User user = userService.selectByName(toName);
            if (user == null){
                return WendaUtil.getJSONString(1,"用户名不存在");
            }
            Message msg = new Message();
            msg.setContent(content);
            msg.setFromId(hostHandler.getUser().getId());
            msg.setToId(user.getId());
            msg.setCreatedDate(new Date());
            msg.setStatus(0);
            msg.setHasRead(0);
            messageService.addMessage(msg);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            LOGGER.error("增加站内信失败" + e.getMessage());
            return WendaUtil.getJSONString(1,"插入站内信失败");
        }
    }
}
