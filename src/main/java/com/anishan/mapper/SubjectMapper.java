package com.anishan.mapper;

import com.anishan.entity.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {

    @Select("select * from subject limit #{begin}, #{limit}")
    List<Subject> selectLimitedSubject(@Param("begin") int begin, @Param("limit") int limit);

    @Update("update subject set name = #{name} where id = #{id}")
    int updateSubject(Subject subject);

    @Insert("insert into subject(name) values(#{name})")
    int insertSubject(String name);

    @Delete("delete from subject where id = #{id}")
    int deleteSubjectById(int id);

}
