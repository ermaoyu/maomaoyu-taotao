package com.maomaoyu.zhihu.mapper;

import com.maomaoyu.zhihu.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * maomaoyu    2018/12/16_14:13
 **/
@Mapper
public interface QuestionMapper {
    String TABLE_NAME = "question";
    String INSERT_FIELD = " title, content, user_id, created_date, comment_count, status";
    String SELECT_FIELD = "id, " + INSERT_FIELD;

    @Select({"insert into ",TABLE_NAME," (",INSERT_FIELD,") values(#{title},#{content},#{userId},#{createdDate},#{commentCount},#{status})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestion(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id=#{id}"})
    Question selectById(int id);

    @Update({"update ",TABLE_NAME," set comment_count=#{count} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("count") int count);

}
