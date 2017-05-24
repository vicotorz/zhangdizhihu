package com.zhihu.dao;

import com.zhihu.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dell on 2017/5/20.
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
    Question select(int id);

    @Update({"update question set title=#{title} where id=#{id}"})
    void updateTitle(Question question);

    @Delete({"delete question where id=#{id}"})
    void deleteById(int id);

}
