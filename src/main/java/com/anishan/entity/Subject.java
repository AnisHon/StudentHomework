package com.anishan.entity;

import lombok.Data;

import java.util.List;

@Data
public class Subject {
    private int id;
    private String name;
    private List<Score> scores;
}
