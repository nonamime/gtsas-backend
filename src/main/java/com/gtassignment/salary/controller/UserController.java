package com.gtassignment.salary.controller;

import com.gtassignment.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Validated
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> usersUpload(@RequestParam(value = "file" ) MultipartFile multipartFile)
            throws Exception {
        //1. currently using ByteArrayResource to pass data around without saving it to temp folder and also
        //   to avoid file not found exception that happens with MultipartFile
        this.userService.enqueueCsvFile(new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Object>> getUsers(
            @RequestParam(value = "minSalary") @Min(0) Double minSalary,
            @RequestParam(value = "maxSalary") Double maxSalary,
            @RequestParam(value = "offset") Integer offset,
            @RequestParam(value = "order") String order) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
