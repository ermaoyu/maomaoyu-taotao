package com.maomaoyu.zhihu.mapper;

import com.maomaoyu.zhihu.bean.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * maomaoyu    2018/12/23_13:16
 **/
@Mapper
public interface CommentMapper {
    String TABLE_NAME = " comment " ;
    String INSERT_FIELDS = " user_id ,content, entity_id , entity_type , created_date , status " ;
    String SELECT_FIELDS = " id , " + INSERT_FIELDS ;

    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELDS,") values(#{userId},#{content},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);

    @Update({"update ",TABLE_NAME," set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    int updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("status") int status);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType}"})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
    Comment selectById(int id);

    @Select({"select count(id) from ",TABLE_NAME," where user_id=#{userId}"})
    int getUserCommentCount(int userId);
}
