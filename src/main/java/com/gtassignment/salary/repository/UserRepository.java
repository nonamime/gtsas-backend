package com.gtassignment.salary.repository;

import com.gtassignment.salary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
