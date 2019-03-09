package com.maomayu.zhihu.service;


import java.util.HashMap;
import java.util.Map;

/**
 * maomaoyu    2018/12/22_19:16
 **/
public interface SensitiveService {

    /**
     *  默认敏感词替换符
     * */
    public String filter(String text);

//    public static void main(String[] argv) {
//        SensitiveService s = new SensitiveService();
//        s.addWord("色情");
//        s.addWord("好色");
//        System.out.print(s.filter("你好*色情XX"));
//    }

}
