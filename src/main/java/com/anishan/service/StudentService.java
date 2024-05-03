package com.anishan.service;

import com.anishan.entity.Account;
import com.anishan.entity.Student;
import com.anishan.exception.ConflictExcption;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface StudentService extends HumanResourceService<Student>{

    @Override
    default boolean addPersonnel(Account account, Student student, Object ...objects) throws ConflictExcption {
        return addStudent(account, student, (Integer) objects[0]);
    }

    /**
     * 添加一个学生并且添加一个account
     * @param account 学生账户不需要设置role和id
     * @param student 学生信息不需要手动设置setAccount和setClazz
     * @param classId 班级id
     * @return 是否插入成功
     * @throws DuplicateKeyException 防止有唯一键冲突
     */

    boolean addStudent(Account account, Student student, int classId) throws ConflictExcption;
    /**
     * 分页查询且只查询学生信息（只有student表和class表）
     * @param page 页码
     * @param limitPrePage 每页个数
     * @return 返回学生信息
     */
    List<Student> getStudentsInfo(int page, int limitPrePage);

    /**
     * 删除学生（自动集联删除）
     * @param studentId 学生ID
     * @return 返回是否能删除
     */
    boolean deleteStudent(int studentId);

    /**
     * 通过username获取所有学生信息（包括成绩）
     * @param username 账号
     * @return 返回student对象
     */
    Student getStudentByUsername(String username);


    Student getStudentById(int studentId);
}
