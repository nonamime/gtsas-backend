package com.gtassignment.salary.service.impl;

import com.gtassignment.salary.model.User;
import com.gtassignment.salary.repository.UserRepository;
import com.gtassignment.salary.service.UserService;
import com.gtassignment.salary.task.QueueExecutor;
import com.gtassignment.salary.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
                User user = userRepository.findById(commonUtils.filterAlphaNumericString(values[0]))
                        .orElse(User.builder().id(commonUtils.filterAlphaNumericString(values[0])).build());
                try {
                    user.setLogin(commonUtils.filterAlphaNumericString(values[1]));
                    user.setName(values[2]);
                    user.setSalary(commonUtils.convertStringToDouble(values[3]));
                    userRepository.save(user);
                } catch (DataIntegrityViolationException ex) {
                    throw new Exception("Duplicate login on line "+ lineNumber);
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
    public void enqueueCsvFile(ByteArrayResource byteArrayResource) throws Exception {
        try {
            queueExecutor.queueFile(new Callable<>() {
                @Override
                public Object call() throws Exception {
                        return parseCsv(byteArrayResource);
                }
            }).get();
        } catch (Exception e) {
            var errorMessages = e.getMessage().split("(Exception:\s*)");
            throw new Exception(errorMessages.length > 1
                    ? errorMessages[1]
                    : "Server error");
        }
    }

    @Override
    public Page<List<User>> getUsersByMinMaxWithOffsetAndOrder(Double minSalary, Double maxSalary, Integer offset,
                                                               String order) throws Exception {
        if (minSalary > maxSalary) throw new Exception("minSalary cannot greater than maxSalary");
        var sortColumnName = order.substring(1);
        //+ - prefix has been validated at controller
        Sort sortBySetting =  Sort.by(order.indexOf("+") == 0
                ? Sort.Order.asc(sortColumnName)
                : Sort.Order.desc(sortColumnName));
        var result = userRepository.findBySalaryGreaterThanEqualAndSalaryLessThanEqual(minSalary, maxSalary,
                PageRequest.of(offset, 30, sortBySetting));
        System.out.println(result);
        return result;
    }
}
