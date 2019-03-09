package com.maomaoyu.zhihu.serivce.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomaoyu.zhihu.bean.Comment;
import com.maomaoyu.zhihu.mapper.CommentMapper;
import com.maomayu.zhihu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * maomaoyu    2018/12/23_13:32
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> getCommentsByEntity(int entityId,int entityType ){
        return commentMapper.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentMapper.addComment(comment);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentMapper.getCommentCount(entityId,entityType);
    }

    public void deleteComment(int entityId, int entityType){
        commentMapper.updateStatus(entityId,entityType,1);
    }

    public Comment getCommentById(int id){
        return commentMapper.selectById(id);
    }

    public int getUserCommentCount(int userId){
        return commentMapper.getUserCommentCount(userId);
    }
}
