package com.maomaoyu.zhihu.bean;

import java.util.Date;

/**
 * maomaoyu    2018/12/23_13:12
 **/
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private int hasRead;
    private String content;
    private String conversationId;
    private Date createdDate;
    private int status;

    public Message() {
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", hasRead=" + hasRead +
                ", content='" + content + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        if(fromId < toId){
            return String.format("%d_%d",fromId,toId);
        }else {
            return String.format("%d_%d",toId,fromId);
        }
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
