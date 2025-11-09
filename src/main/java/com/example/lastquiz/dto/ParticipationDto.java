package com.example.lastquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {
    private Integer id;
    private Integer studentId;
    private Integer quizId;
    private LocalDateTime participationDate;
    private Double score;
}

