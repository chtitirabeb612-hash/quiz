package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "response")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String responseText;

    private Boolean isCorrect = false;

    private Timestamp createdAt;
}
