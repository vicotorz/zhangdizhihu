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
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into message (from_id,to_id,content,created_date,has_read,conversation_id) " +
            "values (#{from_id},#{to_id},#{content},#{created_date},#{has_read},#{conversation_id})"})
    int addMessage(Message message);

    @Select({"select * from message where id=#{id}"})
    Message select(@Param("id") int id);

    @Update({"update message set content=#{content} where id=#{id}"})
    void updateContent(Message message);

    @Delete({"delete message where id=#{id}"})
    void deleteById(@Param("id") int id);

//=====================================================================================================
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset, @Param("limit") int limit);

}
