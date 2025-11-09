package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "participation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    private Double score;

    private Timestamp createdAt;
}

