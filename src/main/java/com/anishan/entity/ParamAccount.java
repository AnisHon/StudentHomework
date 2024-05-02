package com.anishan.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ParamAccount {
    @NotEmpty(message = "账号不能为空")
    private String username;
    @Length(min = 8, max = 16, message = "密码必须为8-16位")
    private String password;
}
