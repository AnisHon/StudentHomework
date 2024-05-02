package com.anishan.controller;

import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Score;
import com.anishan.entity.Student;
import com.anishan.entity.Subject;
import com.anishan.exception.ConflictExcption;
import com.anishan.service.ScoreService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Min;
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
    public String addScore(int subject_id, int student_id, int score) {
        Score scoreParam = new Score()
                .setScore(score)
                .setStudent(new Student().setId(student_id))
                .setSubject(new Subject().setId(subject_id));


        try {

            boolean b = scoreService.addScore(scoreParam);
            if (b) {
                return RestfulEntity.plainSuccessMessage("success").toJson();
            }
        } catch (ConflictExcption e) {
            return RestfulEntity.failMessage(400, e.getMessage()).toJson();
        }
        return RestfulEntity.failMessage(400, "未知").toJson();
    }

}
