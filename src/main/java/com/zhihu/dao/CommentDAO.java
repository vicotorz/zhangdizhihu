package com.zhihu.dao;

import com.zhihu.model.Comment;
import com.zhihu.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dell on 2017/5/20.
 */
@Mapper
public interface CommentDAO {

    @Insert({"insert into comment (content,user_id,entity_id,entity_type,created_date,status) " +
            "values (#{content},#{user_id},#{entity_id},#{entity_type},#{created_date},#{status})"})
    int addComment(Comment comment);

    @Select({"select * from comment where id=#{id}"})
    Comment select(@Param("id") int id);

    @Update({"update comment set content=#{content} where user_id=#{user_id}"})
    void updateContent(Comment comment);

    @Delete({"delete comment where user_id=#{user_id}"})
    void deleteById(@Param("id") int id);

    @Select({"select * from comment where entity_id=#{entity_id} and entity_type=#{entity_type}"})
    List<Comment> getCommentsByEntity(@Param("entity_id") int entity_id, @Param("entity_type") int entity_type);

}