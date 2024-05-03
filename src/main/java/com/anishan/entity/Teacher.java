package com.anishan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Teacher {


    private int id;
    private String name;
    private String gender;
    private Account account;


    public Teacher(ParamTeacher paramTeacher) {
        this.id = -1;
        this.name = paramTeacher.getName();
        this.gender = paramTeacher.getGender();
        this.account = null;
    }
}
