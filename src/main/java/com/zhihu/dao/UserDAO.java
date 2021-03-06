package com.zhihu.dao;

import com.zhihu.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * Created by victorz on 2017/5/20.
 */
@Mapper
public interface UserDAO {

    @Insert({"insert into user (name,password,salt,head_url) " +
            "values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select * from user where id=#{id}"})
    User select(int id);

    @Update({"update user set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete user where id=#{id}"})
    void deleteById(int id);

    @Select({"select * from user where name=#{name}"})
    User selectByName(String name);

    @Update({"update user set head_url=#{headUrl} where id=#{id}"})
    void editImage(User user);


}
