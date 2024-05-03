package com.anishan.mapper;

import com.anishan.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher limit #{begin}, #{limit}")
    List<Teacher> selectLimitedTeacher(@Param("begin") int begin, @Param("limit") int limit);

    @Delete("delete from teacher where id = #{id}")
    int deleteTeacherById(int id);

    @Insert("insert into teacher(name, gender, account_id) values(#{name}, #{gender}, #{account.id})")
    int insertTeacher(Teacher teacher);

    List<Teacher> selectLimitedTeacherLikeSearch(@Param("begin") int begin, @Param("limit") int limit, @Param("search") String search);

    @Select("select teacher.id as id, name, gender from teacher left join account a on a.id = teacher.account_id where username = #{username}")
    Teacher selectTeacherByUsername(String username);
}
