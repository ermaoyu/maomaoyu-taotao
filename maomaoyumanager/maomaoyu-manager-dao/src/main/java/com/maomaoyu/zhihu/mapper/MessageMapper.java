package com.maomaoyu.zhihu.mapper;

import com.maomaoyu.zhihu.bean.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * maomaoyu    2018/12/23_13:16
 **/
@Mapper
public interface MessageMapper {
    String TABLE_NAME = " message " ;
    String INSERT_FIELDS = " from_id , to_id , content , conversation_id , created_date , status ,has_read" ;
    String SELECT_FIELDS = " id , " + INSERT_FIELDS ;

    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELDS,") values(#{fromId},#{toId},#{content},#{conversationId},#{createdDate},#{status},#{hasRead})"})
    int addMessage(Message message);

    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Update({"update ",TABLE_NAME," set has_read=#{hasRead} where id=#{id}"})
    int updateHasRead(@Param("id") int id, @Param("hasRead") int hasRead);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ",INSERT_FIELDS," ,count(id) as id from (select * from ",TABLE_NAME," where from_id =#{userId} or to_id=#{userId} order by id desc)tt group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where conversation_id=#{conversationId}"})
    int getConversationDetailCount(String conversationId);

    @Select({"select count(id) from (select count(id) as id from ",TABLE_NAME," where from_id =#{userId} or to_id=#{userId} group by conversation_id )ff"})
    int getConversationCount(int userId);
}
