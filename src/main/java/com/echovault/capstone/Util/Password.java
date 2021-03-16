package com.echovault.capstone.Util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Password {

    private static final int ROUNDS = 12;
    private static List<String> createdPasswords = new ArrayList<>();

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(ROUNDS));
    }

    public static boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static String randomGen(){
        StringBuilder out = new StringBuilder();
        List<String> specials = new ArrayList<>(Arrays.asList("!", "#", "$", "%", "&"));
        while(out.length() < 14){
            int ran = (int) Math.floor(Math.random() * 10);
            if(ran < 5){
                out.append(specials.get(ran));
            }
            out.append(RandomStringUtils.randomAlphabetic(1));
            out.append(RandomStringUtils.randomAlphanumeric(1));
        }
        createdPasswords.add(out.toString());
        return out.toString();
    }

    public static List<String> getThePassword (){
        return createdPasswords;
    }
}
