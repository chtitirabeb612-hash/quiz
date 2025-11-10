package com.example.lastquiz.repository;

import com.example.lastquiz.entity.QuizValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizValidationRepository extends JpaRepository<QuizValidation, Integer> {
}
