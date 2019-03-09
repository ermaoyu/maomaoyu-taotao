package com.maomaoyu.zhihu.serivce.impl;

import com.maomaoyu.zhihu.bean.Question;
import com.maomayu.zhihu.service.QuestionService;
import com.maomaoyu.zhihu.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * maomaoyu    2018/12/16_15:50
 **/
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    SensitiveServiceImpl sensitiveServiceImpl;

    public Question getById(int id){
        return questionMapper.selectById(id);
    }

    public List<Question> getLatestQuestions(int userId,int offset,int limit){
        return questionMapper.selectLatestQuestion(userId,offset,limit);
    }

    public int addQuestion(Question question){
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词过滤
        question.setTitle(sensitiveServiceImpl.filter(question.getTitle()));
        question.setContent(sensitiveServiceImpl.filter(question.getContent()));
        return questionMapper.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public int updateCommentCount(int id,int count){
        return questionMapper.updateCommentCount(id,count);
    }
}
