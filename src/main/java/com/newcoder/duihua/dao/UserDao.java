package com.newcoder.duihua.dao;

import com.newcoder.duihua.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by wangdong on 2017/8/3.
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name,password,salt,head_url";
    String SELECT_FIELDS = "id"+","+INSERT_FIELDS;
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    User selectById(int id);
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name = #{username}"})
    User selectByName(String username);

    @Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    void updatePassword(User user);

    @Delete({"delete from",TABLE_NAME,"where id = #{id}"})
    void deleteById(int id);
}
