package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.Message;

import java.util.List;

/**
 * maomaoyu    2018/12/23_14:47
 **/
public interface MessageService {


    public int addMessage(Message message);

    public List<Message> getConversationDetail(String conversationId, int offset, int limit);

    public List<Message> getConversationList(int userId, int offset, int limit);

    public int getConvesationUnreadCount(int userId, String conversationId);

    public int ReadMessage(int id);

    public int getConversationDetailCount(String  conversationId);

    public int getConversationCount(int userId);
}
