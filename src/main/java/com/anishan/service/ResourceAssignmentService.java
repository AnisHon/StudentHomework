package com.anishan.service;

import com.anishan.entity.Clazz;
import com.anishan.entity.Subject;

import java.util.List;

public interface ResourceAssignmentService {

    List<Clazz> selectLimitedClass(int page);

    boolean insertClass(String name);

    boolean updateClass(Clazz clazz);

    boolean removeClass(int id);

    List<Subject> selectLimitedSubject(int page);

    boolean updateSubject(Subject subject);

    boolean insertSubject(String name);

    boolean deleteSubjectById(int id);

}
