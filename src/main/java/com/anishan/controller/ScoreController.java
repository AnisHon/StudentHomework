package com.anishan.controller;

import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Score;
import com.anishan.entity.Student;
import com.anishan.entity.Subject;
import com.anishan.exception.ConflictExcption;
import com.anishan.service.ScoreService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ScoreController {

    @Resource
    ScoreService scoreService;

    @ResponseBody
    @GetMapping("/teacher/scores/{page}")
    public String getScores(
            @Min(value = 1, message = "页码不能小于1")
            @PathVariable
            Integer page
    ) {
        List<Score> pagedScore = scoreService.getPagedScore(page);
        return RestfulEntity.successMessage("success", pagedScore).toJsonWithoutNull();
    }

    @ResponseBody
    @PostMapping("/teacher/add-score")
    public String addScore(
            @NotNull Integer subject_id,
            @NotNull Integer student_id,
            @Max(value = 10000, message = "分数不得超过100分（100 * 100）")
            @Min(value = 0, message = "分数不得低于0")
            @NotNull
            Integer score
    ) {
        Score scoreParam = new Score()
                .setScore(score)
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


    @ResponseBody
    @GetMapping("/teacher/remove-score/{id}")
    public String deleteScore(@PathVariable Integer id) {
        boolean b = scoreService.removeScore(id);
        return RestfulEntity.boolMessage(b, "success", 400, "失败").toJson();

    }

}
