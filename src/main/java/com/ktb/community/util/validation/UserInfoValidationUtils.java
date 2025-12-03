package com.ktb.community.util.validation;

import org.springframework.stereotype.Component;

@Component
public class UserInfoValidationUtils {

    // 이메일 검증
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // 비밀번호 검증
    public static boolean isValidPassword(String password) {
        String passwordRegex =
                "^(?=.*[A-Z])" +      // 대문자 최소 1개
                        "(?=.*[a-z])" +       // 소문자 최소 1개
                        "(?=.*\\d)" +         // 숫자 최소 1개
                        "(?=.*[!@#$%^&*(),.?\":{}|<>])" + // 특수문자 최소 1개
                        ".{8,20}$";           // 8자 이상 20자 이하

        return password != null && password.matches(passwordRegex);
    }
}
