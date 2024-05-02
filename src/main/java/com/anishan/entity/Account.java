package com.anishan.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Account {

    private int id;
    @NotEmpty(message = "账号不能为空")
    private String username;
    @Length(min = 8, max = 16, message = "密码必须为8-16位")
    private String password;
    private String role;
    private String email;

    public Account(ParamAccount paramAccount) {
        this.id = -1;
        this.username = paramAccount.getUsername();
        this.password = paramAccount.getPassword();
        this.role = null;
        this.email = null;
    }


}
