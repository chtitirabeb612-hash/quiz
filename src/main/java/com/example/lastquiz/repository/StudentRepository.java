package com.example.lastquiz.repository;

import com.example.lastquiz.entity.Student;
import com.example.lastquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUser(User user);

}
