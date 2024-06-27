package com.anishan.mapper;

import com.anishan.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 分页查询非详细信息，只有class + student
     */
    List<Student> selectLimitedStudents(@Param("beg") int beg, @Param("limit") int limit);

    List<Student> selectLimitedStudentsWithCondition(@Param("beg") int beg, @Param("limit") int limit, @Param("search") String search);

    @Insert("insert into student(name, gender, age, address, account_id, class_id) values(#{name}, #{gender}, #{age}, #{address}, #{account.id}, #{clazz.id})")
    int insertStudent(Student student);

    @Select("select account_id from student where id = #{studentId}")
    Integer selectAccountIdByStudentId(int studentId);

    /**
     * 删除学生
     */
    @Delete("delete from student where id = #{id}")
    int deleteStudentById(int id);

    /**
     * 所有学生信息通过account username
     */
    Student selectStudentByUsername(String username);

    /**
     * 所有学生信息通过student id
     */
    Student selectStudentById(int id);

    @ResultMap("StudentClassMapping")
    @Select("select stu.id as sid, name as sname, username from student stu left join account ac on stu.account_id = ac.id where name like concat('%', #{name}, '%') or username like concat('%', #{name}, '%') limit 0, 10")
    List<Student> selectStudentLikeName(String name);
}
