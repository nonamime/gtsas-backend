package com.gtassignment.salary.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

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
}
