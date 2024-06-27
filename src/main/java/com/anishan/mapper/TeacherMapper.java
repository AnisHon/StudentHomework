package com.anishan.mapper;

import com.anishan.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @ResultMap("teacherMapping")
    @Select("select t.id as id , name, gender, a.username as username from teacher t left join account a on a.id = t.account_id limit  #{begin}, #{limit}")
    List<Teacher> selectLimitedTeacher(@Param("begin") int begin, @Param("limit") int limit);

    @Delete("delete from teacher where id = #{id}")
    int deleteTeacherById(int id);

    @Insert("insert into teacher(name, gender, account_id) values(#{name}, #{gender}, #{account.id})")
    int insertTeacher(Teacher teacher);

    List<Teacher> selectLimitedTeacherLikeSearch(@Param("begin") int begin, @Param("limit") int limit, @Param("search") String search);

    @Select("select teacher.id as id, name, gender from teacher left join account a on a.id = teacher.account_id where username = #{username}")
    Teacher selectTeacherByUsername(String username);

    @Select("select account_id from teacher where id = #{id}")
    int selectTeacherAccountIdById(int id);

    @ResultMap("teacherMapping")
    @Select("select t.id as id, name, gender, a.id as aid, username, email from teacher t left join account a on a.id = t.account_id where t.id = #{id}")
    Teacher selectTeacherById(int id);
}
