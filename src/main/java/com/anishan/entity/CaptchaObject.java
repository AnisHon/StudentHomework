package com.anishan.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CaptchaObject {

    private String image;
    private String uuid;




}
