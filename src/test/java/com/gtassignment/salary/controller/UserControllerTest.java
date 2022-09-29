package com.gtassignment.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "Should return bad request with not file found when upload with empty value")
    void testEmptyFileUpload() {

    }
}
