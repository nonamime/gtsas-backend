package com.gtassignment.salary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    @PostMapping(path = "/users/upload")
    public ResponseEntity<Object> usersUpload(@RequestParam(value = "file") MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) return ResponseEntity.badRequest().body("Not file found");

        System.out.println(multipartFile.getOriginalFilename());

        return ResponseEntity.noContent().build();
    }
}
