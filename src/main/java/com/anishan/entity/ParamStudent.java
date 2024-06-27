package com.anishan.entity;

import jakarta.validation.constraints.*;
import lombok.Data;

import javax.xml.stream.XMLEventWriter;


@Data
public class ParamStudent {
    @NotEmpty(message = "姓名必填")
    private String name;
    @Max(value = 100, message = "年龄最大100")
    @Min(value = 1, message = "年龄最小1")
    @NotNull(message = "性别必填")
    private Integer age;
    @Pattern(regexp = "[男,女]", message = "性别只能是男女")
    private String gender;
    @NotEmpty(message = "住址必填")
    private String address;
}
