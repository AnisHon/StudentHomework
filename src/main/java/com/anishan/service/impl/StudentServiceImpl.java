package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.Clazz;
import com.anishan.entity.Student;
import com.anishan.exception.ConfilictExcption;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.StudentMapper;
import com.anishan.service.AccountService;
import com.anishan.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    StudentMapper studentMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    AccountService accountService;


    /**
     * 添加一个学生并且添加一个account
     * @param account 学生账户不需要设置role和id
     * @param student 学生信息不需要手动设置setAccount和setClazz
     * @param classId 班级id
     * @return 是否插入成功
     * @throws DuplicateKeyException 防止有唯一键冲突
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addStudent(Account account, Student student, int classId) throws ConfilictExcption {
        account.setRole("student");
        student.
                setAccount(account).
                setClazz(new Clazz(classId));

        int i;
        try {
            accountService.addAccount(account);
        } catch (DuplicateKeyException e) {
            ConfilictExcption confilictExcption = new ConfilictExcption("账号："+ account.getUsername() + "冲突");
            confilictExcption.initCause(e);
            throw confilictExcption;
        }

        try {
            i = studentMapper.insertStudent(student);
        } catch (Exception e) {
            ConfilictExcption confilictExcption = new ConfilictExcption("班级ID："+ classId + "不存在");
            confilictExcption.initCause(e);
            throw confilictExcption;
        }

        return i >= 1;
    }

    /**
     * 分页查询且只查询学生信息（只有student表和class表）
     * @param page 页码
     * @param limitPrePage 每页个数
     * @return 返回学生信息
     */
    @Override
    public List<Student> getStudentsInfo(int page, int limitPrePage) {
        return studentMapper.selectLimitedStudents((page - 1) * limitPrePage, limitPrePage);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStudent(int studentId) {
        Integer accountId = studentMapper.selectAccountIdByStudentId(studentId);
        if (accountId == null) return false;
        int i = studentMapper.deleteStudentById(studentId);
        int j = accountMapper.deleteAccountById(accountId);
        return i >= 0 && j >= 0;
    }


}
