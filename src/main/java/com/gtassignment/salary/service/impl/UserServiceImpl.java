package com.gtassignment.salary.service.impl;

import com.gtassignment.salary.model.User;
import com.gtassignment.salary.repository.UserRepository;
import com.gtassignment.salary.service.UserService;
import com.gtassignment.salary.task.QueueExecutor;
import com.gtassignment.salary.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    QueueExecutor queueExecutor;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean parseCsv(ByteArrayResource byteArrayResource) throws Exception {
        if (byteArrayResource.getByteArray().length < 0) throw new Exception("Empty file uploaded");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                byteArrayResource.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            Boolean firstLineSkip = false;
            var lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (!firstLineSkip) {
                    firstLineSkip = true;
                    continue;
                }
                //skip comment line
                if (line.indexOf("#") == 0) continue;
                String[] values = line.split(",");
                User user = userRepository.findById(commonUtils.convertStringToLong(values[0]))
                        .orElse(new User());
                try {
                    user.setLogin(commonUtils.convertStringToInteger(values[1]));
                    user.setName(values[2]);
                    user.setSalary(commonUtils.convertStringToDouble(values[3]));
                    userRepository.save(user);
                } catch (DataIntegrityViolationException ex) {
                    throw new Exception("Duplicate login on line :"+ lineNumber);
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage() + " on line " + lineNumber);
                }
            }
        } catch (IOException ex) {
            throw new Exception("Read File Error");
        }
        return false;
    }

    @Override
    @Async
    public Future<Boolean> enqueueCsvFile(ByteArrayResource byteArrayResource) throws Exception {
        return queueExecutor.queueFile(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    System.out.println("processing file");
                    return parseCsv(byteArrayResource);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }
        });
    }
}
