package com.zhihu.dao;


import com.zhihu.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by victorz on 2017/7/9.
 */
@Mapper
public interface FeedDAO {
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);

    @Insert({"insert into feed (user_id, data, created_date, type) values (#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    @Select({"select id, user_id, data, created_date, type from feed where id=#{id}"})
    Feed getFeedById(int id);


}
