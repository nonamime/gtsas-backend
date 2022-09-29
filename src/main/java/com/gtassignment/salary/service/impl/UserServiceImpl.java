package com.gtassignment.salary.service.impl;

import com.gtassignment.salary.model.User;
import com.gtassignment.salary.repository.UserRepository;
import com.gtassignment.salary.service.UserService;
import com.gtassignment.salary.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonUtils commonUtils;

    @Override
    public Boolean parseCsv(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) throw new Exception("File Not Found");
        if (!multipartFile.getContentType().equals("text/csv")) throw new Exception("File type is not csv");

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                multipartFile.getInputStream(), "UTF-8"))){
            String line;
            Boolean firstLineSkip = false;
            var lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (!firstLineSkip) {
                    firstLineSkip = false;
                    continue;
                }
                //skip comment line
                if (line != null && line.indexOf("#") == 0) continue;
                String[] values = line.split(",");
                User user = userRepository.findById(commonUtils.convertStringToLong(values[0]))
                        .orElse(new User());
                try{
                    user.setLogin(commonUtils.convertStringToInteger(values[1]));
                    user.setName(values[2]);
                    user.setSalary(commonUtils.convertStringToDouble(values[3]));
                    userRepository.save(user);
                } catch (DataIntegrityViolationException ex) {
                    //throw line detail along with exception when duplicate row appears
                    throw new Exception("Duplicate Login on line :"+ lineNumber);
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage() + " on line " + lineNumber);
                }
            }

        } catch (IOException ex) {
            throw new Exception("Read File Error");
        }
        return false;
    }
}
