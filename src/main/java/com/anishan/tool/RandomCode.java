package com.anishan.tool;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomCode {

    private final String RANDOM_TEMPLATE;
    private final Random RANDOM_GENERATOR;
    private final int LENGTH;
    public RandomCode() {
        StringBuilder stringBuilder = new StringBuilder();
        RANDOM_GENERATOR = new Random();
        for (char c = 'a'; c <= 'z'; c++) {
            stringBuilder.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            stringBuilder.append(c);
        }
        for (int i = 0; i <= 9; i++) {
            stringBuilder.append(i);
        }
        RANDOM_TEMPLATE = stringBuilder.toString();
        LENGTH = RANDOM_TEMPLATE.length();
    }

    String getCode(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = RANDOM_GENERATOR.nextInt(LENGTH);
            builder.append(RANDOM_TEMPLATE.charAt(index));
        }
        return builder.toString();
    }



}
