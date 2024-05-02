package com.anishan.service.impl;

import com.anishan.entity.Score;
import com.anishan.mapper.ScoreMapper;
import com.anishan.service.ScoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final int PRE_PAGE_SIZE = 10;
    @Resource
    ScoreMapper scoreMapper;

    /**
     * 分页成绩查询
     * 参数千万不能小于等于0
     * 参数千万不能小于等于0
     * 参数千万不能小于等于0
     * @param page 当前页码
     */
    @Override
    public List<Score> getPagedScore(int page) {
        int index = (page - 1) * PRE_PAGE_SIZE;
        return scoreMapper.selectLimitedScores(index, PRE_PAGE_SIZE);
    }

    @Override
    public boolean addScore(Score score) {
        return false;
    }

}
