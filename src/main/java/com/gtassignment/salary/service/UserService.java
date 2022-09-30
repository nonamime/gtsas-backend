package com.gtassignment.salary.service;

import com.gtassignment.salary.dto.UserRequestParamDto;
import com.gtassignment.salary.model.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    Boolean parseCsv(ByteArrayResource byteArrayResource) throws Exception;

    void enqueueCsvFile(ByteArrayResource byteArrayResource) throws Exception;

    Page<List<User>> getUsersByMinMaxWithOffsetAndOrder(Double minSalary, Double maxSalary, Integer offset, String order)
            throws Exception;

    User getUser(String id) throws Exception;

    void deleteUser(String id) throws Exception;

    void editUser(UserRequestParamDto userDto) throws Exception;

    User addUser(UserRequestParamDto userDto) throws Exception;
}
