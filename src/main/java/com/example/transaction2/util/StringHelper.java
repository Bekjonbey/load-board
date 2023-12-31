package com.example.transaction2.util;


import java.util.Random;

public class StringHelper {

    private static final Random random = new Random();

    public static boolean isValidPassword(String s){
        boolean lower = false;
        boolean upper = false;
        boolean number = false;

        for (int i = 0; i < s.length(); i++){
            if (Character.isUpperCase(s.charAt(i))) upper = true;
            else if (Character.isLowerCase(s.charAt(i))) lower = true;
            else if (Character.isDigit(s.charAt(i))) number = true;
        }

        return lower && upper && number;
    }

    public static String generateVerificationCode(){

        StringBuilder code = new StringBuilder();
        for (int i=0; i<8; i++){
            int a = random.nextInt(2);
            code.append(a == 0 ? String.valueOf(randomLetter()) : String.valueOf(random.nextInt(10)));
        }

        return code.toString();
    }

    public static Character randomLetter(){
        int ch = random.nextInt(26) + 65;
        int lu = random.nextInt(2);
        ch = lu == 0 ? ch + 32 : ch;
        return (char) (ch);
    }

}
