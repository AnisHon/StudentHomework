package com.anishan.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Score {
    private int id;
    private Student student;
    private Subject subject;
    private int score;

}
