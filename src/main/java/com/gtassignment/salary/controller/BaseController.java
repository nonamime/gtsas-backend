package com.gtassignment.salary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

public class BaseController {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception err) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", err.getMessage()));
    }
}
