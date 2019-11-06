package com.fpoly.suppermannh.untils;

import java.util.regex.Pattern;

public class SoUtils {

    public static boolean isVaidSo(String fullName) {
        String patternFullname = "^[0-9]{1,255}$";
        return Pattern.compile(patternFullname).matcher(fullName).matches();
    }
}
