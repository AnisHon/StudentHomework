package com.anishan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoubleScore {

    private int id;
    private Student student;
    private Subject subject;
    private double score;

    public DoubleScore(Score score) {
        this.id = score.getId();
        this.student = score.getStudent();
        this.subject = score.getSubject();
        this.score = score.getScore() / 100.0;
    }

}
