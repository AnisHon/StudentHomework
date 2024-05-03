package com.anishan.mapper;

import com.anishan.entity.Clazz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select * from class limit #{begin}, #{limit}")
    List<Clazz> selectLimitedClass(@Param("begin") int begin, @Param("limit") int limit);

    @Insert("insert into class(name) values(#{name})")
    int insertClass(String name);

    @Update("update class set name = #{name} where id = #{id}")
    int updateClass(Clazz clazz);

    @Delete("delete from class where id = #{id}")
    int deleteClassById(int id);

    @Select("select * from class where name like concat('%', #{search}, '%') limit #{begin}, #{limit}")
    List<Clazz> selectLimitedClassNameLikeSearch(@Param("begin") int begin, @Param("limit") int limit, @Param("search") String search);
}
