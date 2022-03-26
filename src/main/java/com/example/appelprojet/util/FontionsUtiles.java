package com.example.appelprojet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontionsUtiles {
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()){
//            return false;
//        }
//        return true;
    }
}
