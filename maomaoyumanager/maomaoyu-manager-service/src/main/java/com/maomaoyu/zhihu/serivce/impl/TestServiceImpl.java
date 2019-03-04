package com.maomaoyu.zhihu.serivce.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomayu.zhihu.service.TestService;
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
