package com.maomayu.zhihu.service;

import com.maomaoyu.zhihu.bean.Comment;

import java.util.List;

/**
 * maomaoyu    2018/12/23_13:32
 **/
public interface CommentService {


    public List<Comment> getCommentsByEntity(int entityId,int entityType );

    public int addComment(Comment comment);

    public int getCommentCount(int entityId,int entityType);

    public void deleteComment(int entityId, int entityType);

    public Comment getCommentById(int id);

    public int getUserCommentCount(int userId);
}
