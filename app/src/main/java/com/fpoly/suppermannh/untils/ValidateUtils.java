package com.fpoly.suppermannh.untils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidateUtils {

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isVaidFullName(String fullName) {
        String patternFullname = "^[a-z0-9A-Z\\s ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]{1,255}$";
        return Pattern.compile(patternFullname).matcher(fullName).matches();
    }
}
