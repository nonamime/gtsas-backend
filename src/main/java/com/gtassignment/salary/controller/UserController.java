package com.gtassignment.salary.controller;

import com.gtassignment.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/users/upload")
    public ResponseEntity<Object> usersUpload(@RequestParam(value = "file" ) MultipartFile multipartFile)
            throws Exception {

        if (multipartFile.isEmpty()) throw new Exception("File Not Found");

        if (!multipartFile.getContentType().equals("text/csv")) throw new Exception("File type is not csv");

        this.userService.parseCsv(multipartFile);

        return ResponseEntity.noContent().build();
    }
}
