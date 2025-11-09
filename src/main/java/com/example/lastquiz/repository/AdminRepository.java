package com.example.lastquiz.repository;

import com.example.lastquiz.entity.Admin;
import com.example.lastquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // recherche par email contenu dans l'entité liée User
    Optional<Admin> findByUserEmail(String email);
    Optional<Admin> findByUser(User user);
}
