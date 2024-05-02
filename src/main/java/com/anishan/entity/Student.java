package com.anishan.entity;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private int id;
    private String name;
    private Integer age;
    private String gender;
    private String address;
    private Account account;
    private Clazz clazz;
    private List<Score> scores;
}
