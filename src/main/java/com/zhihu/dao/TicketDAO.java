package com.zhihu.dao;

import com.zhihu.model.LoginTicket;
import org.apache.ibatis.annotations.*;


/**
 * Created by victorz on 2017/5/28.
 */
@Mapper
public interface TicketDAO {

    @Select({"select * from login_ticket where user_id=#{userId} and status=0"})
    LoginTicket SelectTicketByid(@Param("userId") int userId);

    @Insert({"insert login_ticket (user_id,ticket,expired,status)" +
            "values (#{userId},#{ticket},#{expired},#{status})"})
    void addTicket(LoginTicket ticket);

    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    void updateticket(@Param("ticket") String ticket, @Param("status") int status);

    @Select({"select * from login_ticket where ticket=#{ticket}"})
    LoginTicket SelectTicket(@Param("ticket") String ticket);

}
