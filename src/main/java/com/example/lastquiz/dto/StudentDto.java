package com.example.lastquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Integer id;
    private String username;
    private String email;
    private String subscriptionStatus; // par ex. "active", "expired"
}

