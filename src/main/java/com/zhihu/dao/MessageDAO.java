package com.zhihu.dao;

import com.zhihu.model.Message;
import com.zhihu.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dell on 2017/5/20.
 */
@Mapper
public interface MessageDAO {
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Insert({"insert into message (from_id,to_id,content,created_date,has_read,conversation_id) " +
            "values (#{from_id},#{to_id},#{content},#{created_date},#{has_read},#{conversation_id})"})
    int addMessage(Message message);

    @Select({"select * from message where id=#{id}"})
    Message select(@Param("id") int id);

    @Update({"update message set content=#{content} where id=#{id}"})
    void updateContent(Message message);

    @Delete({"delete message where id=#{id}"})
    void deleteById(@Param("id") int id);

}
