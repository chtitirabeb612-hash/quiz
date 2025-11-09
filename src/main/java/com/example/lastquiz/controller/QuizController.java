package com.example.lastquiz.controller;

import com.example.lastquiz.entity.Quiz;
import com.example.lastquiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Integer id) {
        Quiz quiz = quizService.getQuizById(id);
        return quiz != null ? ResponseEntity.ok(quiz) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}


