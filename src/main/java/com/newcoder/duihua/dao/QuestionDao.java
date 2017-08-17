package com.newcoder.duihua.dao;

import com.newcoder.duihua.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by wangdong on 2017/8/3.
 */
@Mapper

public interface QuestionDao {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title,content,create_date,user_id,comment_count";
    String SELECT_FIELDS = "id"+","+INSERT_FIELDS;
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values(#{title},#{content},#{createDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);


    List<Question>  selectLatestQuestions(@Param("userId") int userId,
                               @Param("offset") int offset,
                               @Param("limit") int limit);

    @Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    void updatePassword(Question question);

    @Delete({"delete from",TABLE_NAME,"where id = #{id}"})
    void deleteById(int id);
}
