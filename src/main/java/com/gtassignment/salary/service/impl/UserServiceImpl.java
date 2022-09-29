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
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                multipartFile.getInputStream(), "UTF-8"))){
            String line;

            Boolean firstLineSkip = false;
            while ((line = br.readLine()) != null) {
                if (!firstLineSkip || (line != null && line.indexOf("#") != 0)) {
                    firstLineSkip = false;
                    continue;
                }
                String[] values = line.split(",");

                if (userRepository.findOne(new User(1,1,"1234",123.11))) throw new Exception("Duplicate id :" + values[2]);
                try{
                    User user = User.builder()
                            .id(commonUtils.convertStringToInteger(values[0]))
                            .login(commonUtils.convertStringToInteger(values[1]))
                            .name(values[2])
                            .salary(commonUtils.convertStringToDouble(values[3])).build();
                    userRepository.save(user);
                } catch (DataIntegrityViolationException ex) {
                    //throw line detail along with exception when duplicate row appears
                    throw new Exception("Unique constraint on line :"+ line);
                }
            }

        } catch (IOException ex) {
            throw new Exception("Read File Error");
        }
        return false;
    }
}
