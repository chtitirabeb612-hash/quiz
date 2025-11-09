package com.example.lastquiz.service;

import com.example.lastquiz.entity.*;
import com.example.lastquiz.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- Création ----------
    public void createUser(User user, String firstName, String lastName, String extraInfo) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        switch (user.getRole()) {
            case STUDENT -> {
                Student student = Student.builder()
                        .user(savedUser)
                        .firstName(firstName)
                        .lastName(lastName)
                        .level(extraInfo) // niveau
                        .build();
                studentRepository.save(student);
            }
            case PROFESSOR -> {
                Professor professor = Professor.builder()
                        .user(savedUser)
                        .firstName(firstName)
                        .lastName(lastName)
                        .specialty(extraInfo) // spécialité
                        .build();
                professorRepository.save(professor);
            }
            case ADMIN -> {
                Admin admin = Admin.builder()
                        .user(savedUser)
                        .firstName(firstName)
                        .lastName(lastName)
                        .build();
                adminRepository.save(admin);
            }
        }
    }

    // ---------- Mise à jour ----------
    public void updateUser(Integer id, String username, String email, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));

        if (username != null && !username.isBlank()) user.setUsername(username);
        if (email != null && !email.isBlank()) user.setEmail(email);
        if (password != null && !password.isBlank()) user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    // ---------- Suppression ----------
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));

        // Supprimer l'entité spécifique selon le rôle
        switch (user.getRole()) {
            case STUDENT -> studentRepository.findByUser(user).ifPresent(studentRepository::delete);
            case PROFESSOR -> professorRepository.findByUser(user).ifPresent(professorRepository::delete);
            case ADMIN -> adminRepository.findByUser(user).ifPresent(adminRepository::delete);
        }

        // Supprimer le User
        userRepository.delete(user);
    }

    // ---------- Méthodes auxiliaires pour rechercher par User ----------
    public Optional<Student> findStudentByUser(User user) {
        return studentRepository.findByUser(user);
    }

    public Optional<Professor> findProfessorByUser(User user) {
        return professorRepository.findByUser(user);
    }

    public Optional<Admin> findAdminByUser(User user) {
        return adminRepository.findByUser(user);
    }
}
