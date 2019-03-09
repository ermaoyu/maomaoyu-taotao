package com.maomaoyu.zhihu.serivce.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomayu.zhihu.service.WendaService;

/**
 * maomaoyu    2018/12/14_17:49
 **/
@Service
public class WendaServiceImpl implements WendaService {

    public String getMessage(int userId){
        return "Hello Message: " + String.valueOf(userId);
    }
}

