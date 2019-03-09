package com.maomaoyu.zhihu.serivce.impl;

import com.maomayu.zhihu.service.TestService;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * maomaoyu    2019/3/4_21:30
 **/

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String show() {
        return null;
    }
}
