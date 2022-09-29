package com.gtassignment.salary.controller;

import com.gtassignment.salary.model.User;
import com.gtassignment.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> usersUpload(@RequestParam(value = "file" ) MultipartFile multipartFile)
            throws Exception {
        //quick hack when no custom validation
        if (multipartFile.getContentType().equals("text/csv")) throw new Exception("File type is not csv");
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

    @GetMapping
    public ResponseEntity<Page<List<User>>> getUsers(
            @RequestParam(value = "minSalary") @Min(value = 0, message = "Value must be greater equals than 0")
                Double minSalary,
            @RequestParam(value = "maxSalary") @Min(value = 0, message = "Value must be greater equals than 0")
                Double maxSalary,
            @RequestParam(value = "offset") @Min(value = 0, message = "Value must be greater equals than 0")
                Integer offset,
            @RequestParam(value = "order") @Pattern(regexp = "^[-+][a-zA-Z]+$", message = "order format incorrect")
                String order) throws Exception {
        return ResponseEntity.ok(userService.getUsersByMinMaxWithOffsetAndOrder(minSalary, maxSalary, offset, order));
    }
}
