package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 100)
    private String specialty; // 🧠 spécialité du professeur

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private Date subscriptionStartDate;
    private Date subscriptionEndDate;

    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
