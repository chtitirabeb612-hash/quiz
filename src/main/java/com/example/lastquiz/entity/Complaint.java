package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Étudiant qui a fait la réclamation
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "admin_response", columnDefinition = "TEXT")
    private String adminResponse;

    // Administrateur ayant traité la réclamation
    @ManyToOne
    @JoinColumn(name = "handled_by_admin_id")
    private Admin handledByAdmin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    public enum Status {
        PENDING,
        IN_PROGRESS,
        RESOLVED,
        REJECTED
    }
}
