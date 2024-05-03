package com.anishan.service.impl;

import com.anishan.entity.Score;
import com.anishan.exception.ConflictExcption;
import com.anishan.mapper.ScoreMapper;
import com.anishan.service.ScoreService;
import jakarta.annotation.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加一条score，score必须有id和stu id
     * @param score 添加score
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addScore(Score score) throws ConflictExcption {
        Integer id = scoreMapper.isScoreExisted(score);
        int i;
        if (id != null) {
            score.setId(id);
            scoreMapper.updateScore(score);
            return true;
        }

        try {
            i = scoreMapper.insertScore(score);
        } catch (DataIntegrityViolationException e) {
            ConflictExcption conflictExcption = new ConflictExcption("学生或学科不存在");
            conflictExcption.initCause(e.getCause());
            throw conflictExcption;
        }
        return i != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeScore(int id) {
        int i = scoreMapper.removeScore(id);
        return i != 0;
    }

}
