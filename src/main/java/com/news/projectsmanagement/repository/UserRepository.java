package com.news.projectsmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.projectsmanagement.model.Users;


public interface UserRepository extends JpaRepository<Users, Long> {
  public Optional<Users> findByEmail(String email);  
    
}
