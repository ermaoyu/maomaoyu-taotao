package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.User;

import java.util.*;

/**
 * maomaoyu    2018/12/16_15:50
 **/
public interface UserService {


    public User getUser(int id);

    public Map<String ,Object> register(String username,String password,String email);


    public Map<String,Object> login(String username, String password);


    public void logout(String ticket);


    public User selectByName(String name);
}
