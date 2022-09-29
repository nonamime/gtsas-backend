package com.gtassignment.salary.repository;

import com.gtassignment.salary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);

//    @Query(value = "SELECT u FROM Users u where u.salary >= :minSalary AND u.salary <= :maxSalary", nativeQuery = true)
    Page<List<User>> findBySalaryGreaterThanEqualAndSalaryLessThanEqual(@Param("minSalary") Double minSalary,
                                                                        @Param("maxSalary") Double maxSalary,
                                                                        Pageable pageable);

}
