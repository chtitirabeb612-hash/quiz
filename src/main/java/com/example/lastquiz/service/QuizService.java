package com.example.lastquiz.service;

import com.example.lastquiz.entity.Quiz;
import com.example.lastquiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Integer id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Integer id) {
        quizRepository.deleteById(id);
    }
}
