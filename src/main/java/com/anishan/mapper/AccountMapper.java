package com.anishan.mapper;

import com.anishan.entity.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("select * from account where username = #{username} ")
    Account selectAccountByUsername(String username);

    @Insert("insert into account(username, password, role) values(#{username}, #{password}, #{role})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = int.class, before = false)
    void insertAccount(Account account);

    @Select("select count(*) from account where email = #{email}")
    int hasEmail(String email);

    @Select("select email from account where username = #{username}")
    String selectEmailByUsername(String username);

    @Update("update account set password = #{password} where username = #{username}")
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);


    @Update("update account set password = #{password} where email = #{email}")
    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Delete("delete from account where id = #{id}")
    int deleteAccountById(int id);

    @Select("select role from account where username = #{username}")
    String selectRoleByUsername(String username);
}
