package com.gtassignment.salary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private Integer login;

    @Column(name = "name")
    private String name;

    @Column(name = "salary", precision = 10, scale = 2)
    private Double salary;

    public void setSalary(Double value) throws Exception {
        if (value < 0) throw new Exception("Salary value must not less than 0");
        this.salary = value;
    }
}
