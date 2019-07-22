package com.zhihu.dao;

import com.zhihu.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by victorz on 2017/5/20.
 */
@Mapper
public interface QuestionDAO {
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Insert({"insert into question (title,content,user_id,created_date,comment_count) " +
            "values (#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select * from question where id=#{id}"})
    Question select(@Param("id") int id);

    @Update({"update question set title=#{title} where id=#{id}"})
    void updateTitle(Question question);

    @Delete({"delete question where id=#{id}"})
    void deleteById(@Param("id") int id);

    @Select({"select comment_count from question where id=#{questionid}"})
    int getCommentCount(@Param("questionid") int questionid);

    @Select({"update question set comment_count=comment_count+1 where id=#{questionid}"})
    void IncreaceCommentCount(@Param("comment_count") int comment_count, @Param("questionid") int questionid);


}
