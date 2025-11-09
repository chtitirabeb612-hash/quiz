package com.example.lastquiz.repository;

import com.example.lastquiz.entity.Complaint;
import com.example.lastquiz.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByStudent(Student student);
    List<Complaint> findByStatus(Complaint.Status status);
}
