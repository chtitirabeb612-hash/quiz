package com.example.lastquiz.service;

import com.example.lastquiz.entity.Participation;
import com.example.lastquiz.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Participation saveParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    public void deleteParticipation(Integer id) {
        participationRepository.deleteById(id);
    }
}

