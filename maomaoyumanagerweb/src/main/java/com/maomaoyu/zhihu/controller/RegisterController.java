package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomaoyu.zhihu.util.WendaUtil;
import com.maomayu.zhihu.service.RegService;
import com.maomayu.zhihu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

/**
 * maomaoyu    2019/1/17_21:26
 **/
@Controller
public class RegisterController {

//    @Reference
//    EventProducer eventProducer;

    @Reference
    private UserService userService;

    @Reference
    RegService regService;

    @RequestMapping(path = {"/reglogin"},method = {RequestMethod.GET})
    public String regloginPage(Model model, @RequestParam(value = "next",required = false) String next){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path = {"/reg/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String register(Model model,@RequestParam("username") String username,
                           @RequestParam("password")String password,
                           @RequestParam("email")String email,
                           @RequestParam("yzm")String yzm,
                           @RequestParam(value = "next",required = false) String next,
                           @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                           HttpServletResponse response){
        try {
            Map<String, Object> map = userService.register(username, password,email);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
//                eventProducer.fireEvent(new EventModel(EventType.REG).setExt("username",username).setExt("to",email));
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

    @RequestMapping(path = {"/reg/yzm"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String Yzm(@RequestParam("email") String email,@RequestParam("username") String username){
        String yzm = verifyCode();//生成随机的验证码给用户
        if (email == null){
            return WendaUtil.getJSONString(1,"用户邮箱不能为空");
        }
        if (email == null){
            return WendaUtil.getJSONString(1,"用户名不能为空");
        }
        regService.sendUserYZM(email,yzm,username);
        return WendaUtil.getJSONString(0,"发送成功");
    }

    private static  String verifyCode(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }
}
