package com.demo.loginSignup.repository;

import com.demo.loginSignup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    User findById(Long Id);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
}