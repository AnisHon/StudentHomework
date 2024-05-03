package com.anishan.entity;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Getter;

@Getter
public class RestfulEntity<T> {

    private final int code;
    private final String message;
    private final T data;

    private RestfulEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestfulEntity<T> successMessage(String message, T data) {
        return new RestfulEntity<>(200, message, data);
    }

    public static RestfulEntity<String> failMessage(int code, String message) {
        return new RestfulEntity<>(code, message, null);
    }

    public static RestfulEntity<?> plainSuccessMessage(String message) {
        return new RestfulEntity<>(200, message, null);
    }

    public String toJson() {
        return JSON.toJSONString(this, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
    }

    public String toJsonWithoutNull() {
        return JSON.toJSONString(this);
    }

    public static RestfulEntity<?> boolMessage(boolean b, String successMessage,int code, String failMessage) {
        RestfulEntity<?> entity;
        if (b) {
            entity = plainSuccessMessage(successMessage);
        } else {
            entity = failMessage(code, failMessage);
        }
        return entity;
    }

    public static <T> RestfulEntity<?> boolMessage(boolean b, String successMessage, T data,int code, String failMessage) {
        RestfulEntity<?> entity;
        if (b) {
            entity = successMessage(successMessage, data);
        } else {
            entity = failMessage(code, failMessage);
        }
        return entity;
    }
}
