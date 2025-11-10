package com.example.lastquiz.service;

import com.example.lastquiz.entity.Admin;
import com.example.lastquiz.entity.Quiz;
import com.example.lastquiz.entity.QuizValidation;
import com.example.lastquiz.repository.QuizValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QuizValidationService {

    @Autowired
    private QuizValidationRepository quizValidationRepository;

    public QuizValidation validateQuiz(Quiz quiz, Admin admin, String status, String comments) {
        QuizValidation validation = QuizValidation.builder()
                .quiz(quiz)
                .admin(admin)
                .validationStatus(status)
                .comments(comments)
                .validatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        return quizValidationRepository.save(validation);
    }
}
