package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.util.RedisKeyUtil;
import com.maomayu.zhihu.service.RegService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * maomaoyu    2019/1/17_21:31
 **/
@Service
public class RegServiceImpl implements RegService {
    @Autowired
    JedisAdapterImpl jedisAdapterImpl;

//    @Autowired
//    MailSenderUtil mailSenderUtil;

    public String saveRegYZM(String email,String yzm){
        String yzmKey = RedisKeyUtil.getYZM(email);
        //存储到redis中并设置过期时间为3分钟
        return jedisAdapterImpl.setex(yzmKey,yzm);
    }

    public boolean checkUserYZM(String email,String yzm){
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(email)){
            map.put("msgemail","用户邮箱不能为空");
            return false;
        }
        if (StringUtils.isBlank(yzm)){
            map.put("msgyzm","用户验证码不能为空");
            return false;
        }
        String yzmKey = RedisKeyUtil.getYZM(email);
        String checkYZM = jedisAdapterImpl.get(yzmKey);
        if (!yzm.equals(checkYZM)){
            map.put("msgyzm","验证码错误");
            return false;
        }

        return true;
    }

public boolean sendUserYZM(String email,String yzm,String username){
//        Map<String,Object> model = new HashMap<>();
//        model.put("email",email);
//        model.put("yzm",yzm);
//        model.put("username",username);
//        model.put("date",new Date());
//        String subject = "Hello,您注册的验证码为: " + yzm;
//        return mailSenderUtil.sendWithHTMLTemplate(email,subject,"mails/regkap.html",model);
    return true;
    }

}
