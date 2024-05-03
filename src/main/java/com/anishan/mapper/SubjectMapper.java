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

    @Select("select * from subject where name like concat('%', #{search}, '%') limit #{begin}, #{limit}")
    List<Subject> selectLimitedSubjectNameLikeSearch(int begin, int limit, String search);

    @Select("select * from subject where name like concat('%', #{search}, '%')")
    List<Subject> selectSubjectNameLikeSearch(String search);

}
