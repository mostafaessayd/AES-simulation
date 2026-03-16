package com.mycompany.aes;

import java.util.Random;

public class KeyGeneratorUtil {

    public static String generateKey(int length) {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                     + "abcdefghijklmnopqrstuvwxyz"
                     + "0123456789";

        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for(int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            key.append(chars.charAt(index));
        }

        return key.toString();
    }
}