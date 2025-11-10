package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "quiz_validation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String validationStatus; // EX: "VALIDÉ", "REFUSÉ"

    private String comments;

    private Timestamp validatedAt;
}
