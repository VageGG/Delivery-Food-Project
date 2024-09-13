package com.fooddeliveryfinalproject.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ValidEmailAndPassword {

    default boolean isEmailValid(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    default boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters long");
        }

        Pattern numberPattern = Pattern.compile("[0-9]");
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Pattern lowercasePattern = Pattern.compile("[a-z]");
        Pattern specialCharacterPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");

        Matcher number = numberPattern.matcher(password);
        Matcher uppercase = uppercasePattern.matcher(password);
        Matcher lowercase = lowercasePattern.matcher(password);
        Matcher specialCharacter = specialCharacterPattern.matcher(password);

        return number.find() && uppercase.find() && lowercase.find() && specialCharacter.find();
    }
}
