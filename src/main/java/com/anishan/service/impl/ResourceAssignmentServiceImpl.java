package com.anishan.service.impl;

import com.anishan.entity.Clazz;
import com.anishan.entity.Subject;
import com.anishan.mapper.ClassMapper;
import com.anishan.mapper.SubjectMapper;
import com.anishan.service.ResourceAssignmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceAssignmentServiceImpl implements ResourceAssignmentService {
    @Resource
    ClassMapper classMapper;
    @Resource
    SubjectMapper subjectMapper;
    private final int PRE_PAGE_SIZE = 10;

    private boolean isChanged(int i) {
        return i != 0;
    }

    private int calculateIndex(int page) {
        return (page - 1) * PRE_PAGE_SIZE;
    }

    @Override
    public List<Clazz> selectLimitedClass(int page, String search) {
        int index = calculateIndex(page);
        return search == null ?
                classMapper.selectLimitedClass(index, PRE_PAGE_SIZE) :
                classMapper.selectLimitedClassNameLikeSearch(index, PRE_PAGE_SIZE, search);
    }

    @Transactional
    @Override
    public boolean insertClass(String name) {
        int i = classMapper.insertClass(name);
        return isChanged(i);
    }

    @Transactional
    @Override
    public boolean updateClass(Clazz clazz) {

        int i = classMapper.updateClass(clazz);
        return isChanged(i);
    }

    @Override
    public boolean removeClass(int id) {
        int i = classMapper.deleteClassById(id);
        return isChanged(i);
    }

    @Override
    public List<Subject> selectLimitedSubject(int page, String search) {
        int index = calculateIndex(page);
        return search == null ?
                subjectMapper.selectLimitedSubject(index, PRE_PAGE_SIZE) :
                subjectMapper.selectLimitedSubjectNameLikeSearch(index, PRE_PAGE_SIZE, search);
    }

    @Override
    public boolean updateSubject(Subject subject) {
        int i = subjectMapper.updateSubject(subject);
        return isChanged(i);
    }

    @Override
    public boolean insertSubject(String name) {
        int i = subjectMapper.insertSubject(name);
        return isChanged(i);
    }

    @Override
    public boolean deleteSubjectById(int id) {
        int i = subjectMapper.deleteSubjectById(id);
        return isChanged(i);
    }

    @Override
    public List<Subject> selectSubjectsByName(String subjectName) {
        return subjectMapper.selectSubjectNameLikeSearch(subjectName);
    }

    @Override
    public List<Clazz> selectClassByName(String className) {
        return classMapper.selectLimitedClassNameLikeSearch(0, 10, className);
    }
}
