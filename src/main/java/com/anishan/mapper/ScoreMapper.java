package com.anishan.mapper;

import com.anishan.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {

    List<Score> selectLimitedScores(@Param("begin") int begin, @Param("limit") int limit);

    @Insert("insert into score(student_id, subject_id, score) values(#{student.id}, #{subject.id}, #{score})")
    int insertScore(Score score);

    @Select("select id from score where score.subject_id = #{subject.id} and score.student_id = #{student.id}")
    Integer isScoreExisted(Score score);

    @Update("update score s set s.score = #{score} where s.id = #{id}")
    int updateScore(Score score);

    @Delete("delete from score where id = #{id}")
    int removeScore(int id);
}
