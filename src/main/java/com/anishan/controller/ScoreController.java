package com.anishan.controller;

import com.anishan.entity.*;
import com.anishan.exception.ConflictExcption;
import com.anishan.service.ScoreService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScoreController {

    @Resource
    ScoreService scoreService;

    @PostMapping(value = "/teacher/change-score", produces = "application/json")
    public String changeScore(
            @NotNull(message = "id必填")
            Integer id,
            @Max(value = 100, message = "分数不得超过100分（100 * 100）")
            @Min(value = 0, message = "分数不得低于0")
            Double score
    ) {
        boolean b = scoreService.updateScore(id, (int)(score * 100));
        return RestfulEntity.boolMessage(b, "success", 400, "失败").toJson();
    }

    @GetMapping(value = "/teacher/scores/{page}", produces = "application/json")
    public String getScores(
            @Min(value = 1, message = "页码不能小于1")
            @PathVariable
            Integer page,
            Integer order,
            String search
    ) {
        List<Score> pagedScore = scoreService.getPagedScore(page, search, order);
        List<DoubleScore> doubleScores = new ArrayList<>();
        for (Score score : pagedScore) {
            doubleScores.add(new DoubleScore(score));
        }
        return RestfulEntity.successMessage("success", doubleScores).toJsonWithoutNull();
    }

    @PostMapping("/teacher/add-score")
    public String addScore(
            @NotNull Integer subject_id,
            @NotNull Integer student_id,
            @Max(value = 100, message = "分数不得超过100分（100 * 100）")
            @Min(value = 0, message = "分数不得低于0")
            Double score
    ) {
        Score scoreParam = new Score()
                .setScore((int)(score * 100))
                .setStudent(new Student().setId(student_id))
                .setSubject(new Subject().setId(subject_id));

        boolean b;
        try {
           b = scoreService.addScore(scoreParam);
        } catch (ConflictExcption e) {
            return RestfulEntity.failMessage(400, e.getMessage()).toJson();
        }

        return RestfulEntity.boolMessage(b, "success", 400, "未知").toJson();
    }


    @GetMapping("/teacher/remove-score/{id}")
    public String deleteScore(@PathVariable Integer id) {
        boolean b = scoreService.removeScore(id);
        return RestfulEntity.boolMessage(b, "success", 400, "失败").toJson();

    }

}
