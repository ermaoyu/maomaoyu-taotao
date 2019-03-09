package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.User;

/**
 * maomaoyu    2018/12/19_14:47
 **/
public interface HostService {

    public User getUser();

    public void setUser(User user);

    public void clear();
}
