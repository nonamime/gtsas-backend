package com.gtassignment.salary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id", length = 255, unique = true)
    private String id;

    @Column(name = "login", length = 255, unique = true)
    private String login;

    @Lob
    @Column(name = "name", length = 1024)
    private String name;

    @Column(name = "salary", precision = 10, scale = 2)
    private Double salary;

    public void setSalary(Double value) throws Exception {
        if (value < 0) throw new Exception("Salary value must not less than 0");
        this.salary = value;
    }
}
