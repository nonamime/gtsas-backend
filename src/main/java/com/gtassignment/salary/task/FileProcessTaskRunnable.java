package com.gtassignment.salary.task;

import org.springframework.web.multipart.MultipartFile;

public class FileProcessTaskRunnable implements Runnable {

    private MultipartFile multipartFile;

    public FileProcessTaskRunnable(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public void run() {

    }
}
