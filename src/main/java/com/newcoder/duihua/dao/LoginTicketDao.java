package com.newcoder.duihua.dao;

import com.newcoder.duihua.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by wangdong on 2017/8/15.
 */
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id,expired,status,ticket";
    String SELECT_FIELDS = "id"+","+INSERT_FIELDS;
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where ticket = #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Insert({"insert into",TABLE_NAME ,"(",INSERT_FIELDS,") values (#{userId},#{expired},#{status},#{ticket})"})
     int addTicket(LoginTicket ticket);

    @Update({"update",TABLE_NAME,"set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("status") int status,@Param("ticket") String ticket);

}
