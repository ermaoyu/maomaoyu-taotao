package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomayu.zhihu.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * maomaoyu    2019/3/4_23:21
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    @Reference
    private TestService testService;

    @RequestMapping(path = {"/show"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String show(){
        System.out.println(testService);
        return "maomaoyu";
    }



}
