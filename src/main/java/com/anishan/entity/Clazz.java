package com.anishan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Clazz {

    private int id;
    private String name;

    public Clazz(int id) {
        this.id = id;
    }

}
