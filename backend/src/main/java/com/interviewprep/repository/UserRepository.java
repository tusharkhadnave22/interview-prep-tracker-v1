package com.interviewprep.repository;

import com.interviewprep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByNameAsc();
}
