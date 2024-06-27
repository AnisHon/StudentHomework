package com.anishan.service;

import com.anishan.entity.Score;
import com.anishan.exception.ConflictExcption;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreService {




    /**
     * 分页成绩查询
     * 参数千万不能小于等于0
     * 参数千万不能小于等于0
     * 参数千万不能小于等于0
     *
     * @param page  当前页码
     * @param name 查询的名字
     * @param order 查询的顺序
     */
    List<Score> getPagedScore(int page, String name, Integer order);

    /**
     * 添加一条score
     * @param score 添加score
     */
    boolean addScore(Score score) throws ConflictExcption;


    boolean removeScore(int id);

    boolean updateScore(int id, int score);
}
