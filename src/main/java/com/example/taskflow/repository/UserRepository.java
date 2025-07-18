package com.example.taskflow.repository;

import com.example.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Override
    Optional<User> findByUsername(String username);
}
