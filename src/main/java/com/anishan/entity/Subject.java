package com.anishan.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Subject {
    private int id;
    private String name;
    private List<Score> scores;
}
