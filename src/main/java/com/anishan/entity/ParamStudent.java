package com.anishan.entity;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class ParamStudent {
    @NotEmpty
    private String name;
    @Max(value = 100, message = "年龄最大100")
    @Min(value = 1, message = "年龄最小1")
    private Integer age;
    @Pattern(regexp = "[男,女]", message = "性别只能是男女")
    private String gender;
    @NotNull
    private String address;
}
