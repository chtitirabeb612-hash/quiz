package com.example.lastquiz.service;

import com.example.lastquiz.entity.Complaint;
import com.example.lastquiz.entity.Student;
import com.example.lastquiz.entity.Admin;
import com.example.lastquiz.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Integer id) {
        return complaintRepository.findById(id);
    }

    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(Integer id) {
        complaintRepository.deleteById(id);
    }

    public List<Complaint> getComplaintsByStudent(Student student) {
        return complaintRepository.findByStudent(student);
    }

    public List<Complaint> getComplaintsByStatus(Complaint.Status status) {
        return complaintRepository.findByStatus(status);
    }

    public Complaint updateStatus(Integer id, Complaint.Status status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        complaint.setStatus(status);
        if (status == Complaint.Status.RESOLVED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        return complaintRepository.save(complaint);
    }

    public Complaint respondToComplaint(Integer id, String response, Integer adminId, Admin admin) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));

        complaint.setAdminResponse(response);
        complaint.setHandledByAdmin(admin);
        complaint.setStatus(Complaint.Status.RESOLVED);
        complaint.setResolvedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }
}
