package com.anishan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Student {

    private int id;
    private String name;
    private Integer age;
    private String gender;
    private String address;
    private Account account;
    private Clazz clazz;
    private List<Score> scores;

    public Student(ParamStudent student) {
        this.id = -1;
        this.name = student.getName();
        this.age = student.getAge();
        this.address = student.getAddress();
        this.gender = student.getGender();
        account = null;
        clazz = null;
        scores = null;
    }

}
