package com.gtassignment.salary.service;

import org.springframework.core.io.ByteArrayResource;

import java.util.concurrent.Future;

public interface UserService {

    Boolean parseCsv(ByteArrayResource byteArrayResource) throws Exception;

    Future<Boolean> enqueueCsvFile(ByteArrayResource byteArrayResource) throws Exception;
}
