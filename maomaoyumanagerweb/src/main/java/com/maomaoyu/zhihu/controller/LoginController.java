package com.maomaoyu.zhihu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.maomayu.zhihu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * maomaoyu    2018/12/19_14:45
 **/
@Controller
public class LoginController {

//    @Reference
//    EventProducer eventProducer;

    @Reference
    private UserService userService;

    @RequestMapping(value = "/login/",method = {RequestMethod.POST})
    public String login(Model model,@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next",required = false) String next,
                        @RequestParam("rememberme") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
//                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
//                        .setExt("username", username).setExt("email", "871365987@qq.com")
//                        .setActorId((int)map.get("userId")));
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next; //边界条件判断
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"} ,method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/" ;
    }
}
