package com.anishan.mapper;

import com.anishan.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> selectLimitedStudents(@Param("beg") int beg, @Param("limit") int limit);

    @Insert("insert into student(name, gender, age, address, account_id, class_id) values(#{name}, #{gender}, #{age}, #{address}, #{account.id}, #{clazz.id})")
    int insertStudent(Student student);

    @Select("select account_id from student where id = #{studentId}")
    Integer selectAccountIdByStudentId(int studentId);

    @Delete("delete from student where id = #{id}")
    int deleteStudentById(int id);

    Student selectStudentByUsername(String username);

}
