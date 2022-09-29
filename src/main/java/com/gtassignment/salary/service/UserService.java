package com.gtassignment.salary.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Boolean parseCsv(MultipartFile multipartFile) throws Exception;
}
