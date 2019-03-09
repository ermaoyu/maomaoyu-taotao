package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomayu.zhihu.service.WendaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * maomaoyu    2018/12/14_16:42
 **/
@Controller
public class SettingController {

    @Reference
    WendaService wendaService;

    @RequestMapping(path = {"/regHtml"},method = {RequestMethod.GET,RequestMethod.POST})
    public String regHtml(){
        return "reg";
    }

    @RequestMapping(path = {"/setting"},method = {RequestMethod.GET})
    @ResponseBody
    public String settingg(HttpSession httpSession){
        return "Setting OK. " + wendaService.getMessage(1);
    }
}
