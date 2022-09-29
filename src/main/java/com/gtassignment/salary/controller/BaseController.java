package com.gtassignment.salary.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseController {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception err) {
        System.out.println(err.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message1", err.getMessage()));
    }

    //Uniqueness violated
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleInvalidParamException(Exception err) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections
                        .singletonMap("mesaage", "Duplicated data, %s".format(err.getMessage())));
    }
}
