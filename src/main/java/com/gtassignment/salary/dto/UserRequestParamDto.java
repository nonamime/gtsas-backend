package com.gtassignment.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestParamDto {

    @Size(min = 1, message = "Id should not less than 1 character")
    @Size(max = 255, message = "Id should not more than 255 character")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "Id should be alphanumeric")
    private String id;

    @Size(min = 1, message = "Login should not less than 1 character")
    @Size(max = 255, message = "Login should not more than 255 character")
    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "Login should be alphanumeric")
    private String login;

    @Size(min = 1, message = "Name should not less than 1 character")
    @Size(max = 1024, message = "Name should not more than 1024 character")
    private String name;

    @Min(value = 0, message = "Salary should not less than 0")
    private Double salary;
}
