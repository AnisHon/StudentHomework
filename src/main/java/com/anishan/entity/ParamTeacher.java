package com.anishan.entity;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ParamTeacher {
    @NotEmpty
    private String name;
    @Pattern(regexp = "[男,女]", message = "性别只能是男女")
    private String gender;
}
