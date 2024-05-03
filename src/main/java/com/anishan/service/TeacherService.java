package com.anishan.service;

import com.anishan.entity.Account;
import com.anishan.entity.Teacher;
import com.anishan.exception.ConflictExcption;

import java.util.List;

public interface TeacherService extends HumanResourceService<Teacher>{

    default boolean addPersonnel(Account account, Teacher teacher, Object ...objects) throws ConflictExcption {
        return addTeacher(account, teacher);
    }

    boolean addTeacher(Account account, Teacher teacher) throws ConflictExcption;

    List<Teacher> getPagedTeacherInfo(int page, String search);
}
