package com.anishan.entity;

import lombok.Data;

@Data
public class Score {
    private int id;
    private Student student;
    private Subject subject;
    private int score;

}
