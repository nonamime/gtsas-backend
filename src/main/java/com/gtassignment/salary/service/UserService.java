package com.gtassignment.salary.service;

import org.springframework.core.io.ByteArrayResource;

public interface UserService {

    Boolean parseCsv(ByteArrayResource byteArrayResource) throws Exception;

    void enqueueCsvFile(ByteArrayResource byteArrayResource) throws Exception;
}
