package com.example.lastquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "guest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private String email;

    private Timestamp createdAt;
}
