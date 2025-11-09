package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "quiz")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(unique = true)
    private String code;

    private Integer duration; // en minutes

    private Timestamp createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;
}

