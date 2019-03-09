package com.maomaoyu.zhihu.serivce.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomaoyu.zhihu.bean.User;
import com.maomayu.zhihu.service.HostService;
import org.springframework.stereotype.Component;

/**
 * maomaoyu    2018/12/19_14:47
 **/
@Service
public class HostHandler implements HostService {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
