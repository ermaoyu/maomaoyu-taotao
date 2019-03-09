package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.bean.Message;
import com.maomayu.zhihu.service.MessageService;
import com.maomaoyu.zhihu.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * maomaoyu    2018/12/23_14:47
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveServiceImpl sensitiveServiceImpl;

    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveServiceImpl.filter(message.getContent()));
        return messageMapper.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageMapper.getConversationDetail(conversationId, (offset - 1) * limit, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageMapper.getConversationList(userId, (offset - 1) * limit, limit);
    }

    public int getConvesationUnreadCount(int userId, String conversationId) {
        return messageMapper.getConvesationUnreadCount(userId, conversationId);
    }

    public int ReadMessage(int id){
        return messageMapper.updateHasRead(id,1);
    }
   public int getConversationDetailCount(String  conversationId){
        return messageMapper.getConversationDetailCount(conversationId);
   }

   public int getConversationCount(int userId){
        return messageMapper.getConversationCount(userId);
   }
}
