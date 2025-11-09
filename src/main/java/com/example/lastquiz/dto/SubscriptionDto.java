package com.example.lastquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Integer id;
    private Integer studentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // "active" ou "expired"
}

