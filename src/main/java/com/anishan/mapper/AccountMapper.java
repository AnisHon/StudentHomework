package com.anishan.mapper;

import com.anishan.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.Mapping;

@Mapper
public interface AccountMapper {

    @Select("select * from account where username = #{username} ")
    Account selectAccountByUsername(String username);

    @Insert("insert into account(username, password, role) values(#{username}, #{password}, #{role})")
    void insertAccount(Account account);

}
