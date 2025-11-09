package com.example.lastquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Integer id;
    private String title;
    private String description;
    private Integer professorId;
    private LocalDateTime createdAt;
}

