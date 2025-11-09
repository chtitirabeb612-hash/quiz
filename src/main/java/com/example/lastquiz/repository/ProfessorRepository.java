package com.example.lastquiz.repository;

import com.example.lastquiz.entity.Professor;
import com.example.lastquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    Optional<Professor> findByUser(User user);

}
