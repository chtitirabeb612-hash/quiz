package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private String questionText;

    private Timestamp createdAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Response> responses;
}

