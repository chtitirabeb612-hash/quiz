package com.example.lastquiz.controller;

import com.example.lastquiz.entity.User;
import com.example.lastquiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.lastquiz.service.UserManagementService;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserManagementService userManagementService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user,
                                             @RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String extraInfo) {

        userManagementService.createUser(user, firstName, lastName, extraInfo); // appel correct
        return ResponseEntity.ok("Utilisateur créé avec succès !");
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}


