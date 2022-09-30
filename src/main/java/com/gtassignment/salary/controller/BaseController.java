package com.gtassignment.salary.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BaseController {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception err) {
        System.out.println(err.getStackTrace().toString());
        return ResponseEntity.status(BAD_REQUEST)
                .body(Collections.singletonMap("message", err.getMessage()));
    }

    //Uniqueness violated
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleInvalidParamException(Exception err) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(Collections
                        .singletonMap("mesaage", "Duplicate data existed"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<HashMap<String,Object>> errors = new ArrayList<>();
        //Compile invalid field error messages into map and output to json with ResponseEntity
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            HashMap<String, Object> fieldErrorDetail = new HashMap<>();
            fieldErrorDetail.put("Field : ", fieldError.getField());
            fieldErrorDetail.put("Error Message : ", fieldError.getDefaultMessage());
            fieldErrorDetail.put("Rejected value : ", fieldError.getRejectedValue());
            errors.add(fieldErrorDetail);
        }
        return ResponseEntity.status(BAD_REQUEST).body((Collections
                .singletonMap("mesaage", errors)));
    }
}