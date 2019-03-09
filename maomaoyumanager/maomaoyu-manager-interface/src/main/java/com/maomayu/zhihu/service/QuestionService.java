package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.Question;

import java.util.List;

/**
 * maomaoyu    2018/12/16_15:50
 **/
public interface QuestionService {

    public Question getById(int id);

    public List<Question> getLatestQuestions(int userId,int offset,int limit);

    public int addQuestion(Question question);

    public int updateCommentCount(int id,int count);
}
