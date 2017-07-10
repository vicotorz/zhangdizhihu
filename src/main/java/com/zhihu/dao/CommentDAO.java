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
            "values (#{content},#{userid},#{entityid},#{entitytype},#{createddate},#{status})"})
    int addComment(Comment comment);

    @Select({"select * from comment where id=#{id}"})
    Comment select(@Param("id") int id);

    @Update({"update comment set content=#{content} where userid=#{userid}"})
    void updateContent(Comment comment);

    @Delete({"delete comment where userid=#{userid}"})
    void deleteById(@Param("id") int id);

    @Select({"select id,content,user_id,entity_id,entity_type,created_date,status " +
            "from comment where entity_id=#{entity_id} and entity_type=#{entity_type}"})
    List<Comment> getCommentsByEntity(@Param("entity_id") int entity_id, @Param("entity_type") int entity_type);

    @Select({"select * from comment where id=#{id}"})
    Comment getCommentById(@Param("id") int id);

    @Select({"select count(id) from comment where userid=#{userId}"})
    int getUserCommentCount(int userId);

    //Comment定义
//            private int id;//评论id
//            private String content;//评论内容
//            private int user_id;//评论用户id
//            private int entity_id;//--->questionId  & commentId
//            private int entity_type;//question & comment
//            private Date created_date;
//            private int status;//被删除状态
}
