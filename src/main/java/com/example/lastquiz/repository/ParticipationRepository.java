package com.example.lastquiz.repository;

import com.example.lastquiz.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
}

