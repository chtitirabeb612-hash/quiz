package com.example.lastquiz.controller;

import com.example.lastquiz.entity.Participation;
import com.example.lastquiz.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@CrossOrigin(origins = "*")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    @PostMapping
    public Participation createParticipation(@RequestBody Participation participation) {
        return participationService.saveParticipation(participation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Integer id) {
        participationService.deleteParticipation(id);
        return ResponseEntity.noContent().build();
    }
}
