package com.joaotech.chatservice.configuration;

public class TokenGeneratorTest {

    public static void main(String[] args) {
        System.out.println(TokenGenerator.getNew());
        System.out.println(TokenGenerator.getNew());
        System.out.println(TokenGenerator.getNew());
        System.out.println(TokenGenerator.getNew());
        System.out.println(TokenGenerator.getNew());

        System.out.println("comecou");
        for (int i = 0; i < 10000; i++) {
            TokenGenerator.getNew(20);
        }
        System.out.println("acabou");

    }

}
