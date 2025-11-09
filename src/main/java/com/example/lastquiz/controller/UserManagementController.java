package com.example.lastquiz.controller;

import com.example.lastquiz.entity.User;
import com.example.lastquiz.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @RequestBody User user,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String extraInfo) {

        userManagementService.createUser(user, firstName, lastName, extraInfo);
        return ResponseEntity.ok("Utilisateur créé avec succès !");
    }
}
