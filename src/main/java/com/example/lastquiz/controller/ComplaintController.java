package com.example.lastquiz.controller;

import com.example.lastquiz.entity.*;
import com.example.lastquiz.repository.AdminRepository;
import com.example.lastquiz.repository.StudentRepository;
import com.example.lastquiz.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    // 🔹 Un étudiant crée une réclamation
    @PostMapping("/add/{studentId}")
    public ResponseEntity<Complaint> createComplaint(@PathVariable Integer studentId, @RequestBody Complaint complaint) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        complaint.setStudent(student);
        complaint.setStatus(Complaint.Status.PENDING);
        return ResponseEntity.ok(complaintService.saveComplaint(complaint));
    }

    // 🔹 L’administrateur consulte toutes les réclamations
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // 🔹 L’administrateur change le statut
    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Integer id, @RequestParam Complaint.Status status) {
        return ResponseEntity.ok(complaintService.updateStatus(id, status));
    }

    // 🔹 L’administrateur répond à une réclamation
    @PutMapping("/{id}/respond/{adminId}")
    public ResponseEntity<Complaint> respondToComplaint(
            @PathVariable Integer id,
            @PathVariable Integer adminId,
            @RequestBody String response) {

        Admin admin = adminRepository.findById(adminId).orElse(null);
        if (admin == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(complaintService.respondToComplaint(id, response, adminId, admin));
    }

    // 🔹 Un étudiant consulte ses réclamations
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Complaint>> getComplaintsByStudent(@PathVariable Integer studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaintService.getComplaintsByStudent(student));
    }
}
