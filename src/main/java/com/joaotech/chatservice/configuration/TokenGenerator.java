package com.joaotech.chatservice.configuration;

import java.util.Random;

public class TokenGenerator {

    private static final Integer DEFAULT_LENGTH = 15;

    public static String getNew() {
        return generate(DEFAULT_LENGTH);
    }

    public static String getNew(int length) {
        return generate(length);
    }

    private static String generate(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
