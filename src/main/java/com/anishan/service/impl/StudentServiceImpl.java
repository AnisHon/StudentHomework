package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.Clazz;
import com.anishan.entity.Student;
import com.anishan.exception.ConflictExcption;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.StudentMapper;
import com.anishan.service.AccountService;
import com.anishan.service.StudentService;
import com.anishan.tool.EnumRole;
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

    private boolean doAddStudent(Account account, Student student, int classId, String role) throws ConflictExcption {
        if (!role.equals(EnumRole.STUDENT) && !role.equals(EnumRole.STUDENT_REPRESENTATIVE)) {
            throw new ConflictExcption("不支持的角色");
        }
        account.setRole(role);
        student.
                setAccount(account).
                setClazz(new Clazz(classId));

        int i;
        try {
            accountService.addAccount(account);
        } catch (DuplicateKeyException e) {
            ConflictExcption conflictExcption = new ConflictExcption("账号："+ account.getUsername() + "冲突");
            conflictExcption.initCause(e);
            throw conflictExcption;
        }

        try {
            i = studentMapper.insertStudent(student);
        } catch (Exception e) {
            ConflictExcption conflictExcption = new ConflictExcption("班级ID："+ classId + "不存在");
            conflictExcption.initCause(e);
            throw conflictExcption;
        }

        return i >= 1;
    }

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
    public boolean addStudent(Account account, Student student, int classId) throws ConflictExcption {
        return doAddStudent(account, student, classId, EnumRole.STUDENT);
//        account.setRole("student");
//        student.
//                setAccount(account).
//                setClazz(new Clazz(classId));
//
//        int i;
//        try {
//            accountService.addAccount(account);
//        } catch (DuplicateKeyException e) {
//            ConflictExcption conflictExcption = new ConflictExcption("账号："+ account.getUsername() + "冲突");
//            conflictExcption.initCause(e);
//            throw conflictExcption;
//        }
//
//        try {
//            i = studentMapper.insertStudent(student);
//        } catch (Exception e) {
//            ConflictExcption conflictExcption = new ConflictExcption("班级ID："+ classId + "不存在");
//            conflictExcption.initCause(e);
//            throw conflictExcption;
//        }

//        return i >= 1;
    }

    @Override
    public boolean addRepresentiveStudent(Account account, Student student, int classId) throws ConflictExcption {
        return doAddStudent(account, student, classId, EnumRole.STUDENT_REPRESENTATIVE);
    }

    /**
     * 分页查询且只查询学生信息（只有student表和class表）
     *
     * @param page         页码
     * @param limitPrePage 每页个数
     * @param search       模糊匹配
     * @return 返回学生信息
     */
    @Override
    public List<Student> getStudentsInfo(int page, int limitPrePage, String search) {
        int index = (page - 1) * limitPrePage;

        return search == null ?
                studentMapper.selectLimitedStudents(index, limitPrePage) :
                studentMapper.selectLimitedStudentsWithCondition(index, limitPrePage, search);
    }

    /**
     * 删除学生（自动集联删除）
     * @param studentId 学生ID
     * @return 返回是否能删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStudent(int studentId) {
        Integer accountId = studentMapper.selectAccountIdByStudentId(studentId);
        if (accountId == null) return false;
//        int i = studentMapper.deleteStudentById(studentId);
        int j = accountMapper.deleteAccountById(accountId);
        return j >= 0;
    }

    /**
     * 通过username获取所有学生信息（包括成绩）
     * @param username 账号
     * @return 返回student对象
     */
    @Override
    public Student getStudentByUsername(String username) {
        return studentMapper.selectStudentByUsername(username);
    }

    /**
     * 通过student id获取所有学生信息（包括成绩）
     * @param studentId 账号
     * @return 返回student对象
     */
    @Override
    public Student getStudentById(int studentId) {
        return studentMapper.selectStudentById(studentId);
    }

    @Override
    public List<Student> getStudentByName(String name) {
        return studentMapper.selectStudentLikeName(name);
    }


}
