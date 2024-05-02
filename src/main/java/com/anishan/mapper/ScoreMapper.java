package com.anishan.mapper;

import com.anishan.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreMapper {

    List<Score> selectLimitedScores(@Param("begin") int begin, @Param("limit") int limit);

}
