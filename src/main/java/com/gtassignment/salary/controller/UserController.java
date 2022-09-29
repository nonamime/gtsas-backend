package com.gtassignment.salary.controller;

import com.gtassignment.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
        //1. result variable assignment is just making it a blocking operation to wait for the process result
        //   if no exception thrown mean executed successfully and vice versa
        //2. currently using ByteArrayResource to pass data around without saving it to temp folder and also
        //   to avoid file not found exception that happens with MultipartFile
        var result = this.userService.enqueueCsvFile(new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        });
        return ResponseEntity.ok().build();
    }
}
