package com.anishan.mapper;

import com.anishan.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> selectLimitedStudents(@Param("beg") int beg, @Param("limit") int limit);

    @Insert("insert into student(name, gender, address, account_id, class_id) values(#{name}, #{gender}, #{address}, #{account.id}, #{clazz.id})")
    int insertStudent(Student student);

}
