package com.echovault.capstone.Util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean goodQualityPassword(String passwordAttempt){
        int len = passwordAttempt.length();
        if(len < 8 || len > 20) return false;
        Pattern upper = Pattern.compile("\\p{Upper}");
        Pattern lower = Pattern.compile("\\p{Lower}");
        Pattern digit = Pattern.compile("\\d");
        Pattern specialChar = Pattern.compile("\\W");
        Matcher matcherUp = upper.matcher(passwordAttempt);
        Matcher matcherLow = lower.matcher(passwordAttempt);
        Matcher matcherNum = digit.matcher(passwordAttempt);
        Matcher special = specialChar.matcher(passwordAttempt);
        return matcherUp.find()
                && matcherLow.find()
                && matcherNum.find()
                && ( special.find() || passwordAttempt.contains("_") );
    }
}
