package com.maomaoyu.zhihu.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * maomaoyu    2019/1/5_14:43
 **/
public class Feed {
    private int id;
    private int type;
    private int userId;
    private Date createdDate;
    private String data;

    private JSONObject dataJSON = null;

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", data='" + data + '\'' +
                ", dataJSON=" + dataJSON +
                '}';
    }

    public Feed() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }

    public String get(String key){
        return dataJSON == null ? null : dataJSON.getString(key);
    }
}
