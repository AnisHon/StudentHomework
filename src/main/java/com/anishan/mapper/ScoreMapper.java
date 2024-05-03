package com.anishan.mapper;

import com.anishan.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {

    int ASCENDING = 0;
    int DESENDING = 1;

    /**
     * 包括
     * student id
     * student name
     * account username
     * subject id
     * subject name
     * score id
     * score->score
     */
    List<Score> selectLimitedScores(@Param("begin") int begin, @Param("limit") int limit);

    List<Score> selectLimitedScoresWithCondidtion(@Param("begin") int begin,
                                                  @Param("limit") int limit,
                                                  @Param("order") int order,
                                                  @Param("search") String search
    );

    @Insert("insert into score(student_id, subject_id, score) values(#{student.id}, #{subject.id}, #{score})")
    int insertScore(Score score);

    @Select("select id from score where score.subject_id = #{subject.id} and score.student_id = #{student.id}")
    Integer isScoreExisted(Score score);

    @Update("update score s set s.score = #{score} where s.id = #{id}")
    int updateScore(Score score);

    @Delete("delete from score where id = #{id}")
    int removeScore(int id);
}
