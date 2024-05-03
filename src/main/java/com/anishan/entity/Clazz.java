package com.anishan.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Clazz {

    @NotNull
    private int id;
    @NotEmpty
    private String name;

    public Clazz(int id) {
        this.id = id;
    }

}
