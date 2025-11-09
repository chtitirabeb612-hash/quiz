package com.example.lastquiz.controller;

import com.example.lastquiz.entity.User;
import com.example.lastquiz.repository.UserRepository;
import com.example.lastquiz.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }
}
