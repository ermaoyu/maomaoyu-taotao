package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.util.RedisKeyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * maomaoyu    2019/1/17_21:31
 **/
public interface RegService {

//    @Autowired
//    MailSenderUtil mailSenderUtil;

    public String saveRegYZM(String email,String yzm);

    public boolean checkUserYZM(String email,String yzm);

    public boolean sendUserYZM(String email,String yzm,String username);
//        Map<String,Object> model = new HashMap<>();
//        model.put("email",email);
//        model.put("yzm",yzm);
//        model.put("username",username);
//        model.put("date",new Date());
//        String subject = "Hello,您注册的验证码为: " + yzm;
//        return mailSenderUtil.sendWithHTMLTemplate(email,subject,"mails/regkap.html",model);
//    }

}
