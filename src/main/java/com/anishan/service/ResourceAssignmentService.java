package com.anishan.service;

import com.anishan.entity.Clazz;
import com.anishan.entity.Subject;

import java.util.List;

public interface ResourceAssignmentService {

    List<Clazz> selectLimitedClass(int page, String search);

    boolean insertClass(String name);

    boolean updateClass(Clazz clazz);

    boolean removeClass(int id);

    List<Subject> selectLimitedSubject(int page, String search);

    boolean updateSubject(Subject subject);

    boolean insertSubject(String name);

    boolean deleteSubjectById(int id);

    List<Subject> selectSubjectsByName(String subjectName);


    List<Clazz> selectClassByName(String className);
}
