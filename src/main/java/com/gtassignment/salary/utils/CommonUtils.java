package com.gtassignment.salary.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public Integer convertStringToInteger(String input) throws Exception {
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException ex) {
            throw new Exception("Wrong Type on field " + input);
        }
    }

    public Long convertStringToLong(String input) throws Exception {
        try {
            return Long.valueOf(input);
        } catch (NumberFormatException ex) {
            throw new Exception("Wrong Type on field " + input);
        }
    }

    public Double convertStringToDouble(String input) throws Exception {
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException ex) {
            throw new Exception("Wrong Type on field " + input);
        }
    }

    public String filterAlphaNumericString(String input) throws Exception {
        if (!input.matches("[0-9a-zA-Z]+")) throw new Exception(input + " is not alphanumeric");
        return input;
    }
}
