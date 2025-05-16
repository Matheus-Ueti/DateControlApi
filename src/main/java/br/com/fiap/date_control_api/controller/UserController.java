package br.com.fiap.date_control_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.date_control_api.model.User;
import br.com.fiap.date_control_api.model.UserRole;
import br.com.fiap.date_control_api.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
public class UserController {
    
    @Autowired UserRepository repository;
    @Autowired PasswordEncoder encoder;
    
    @PostMapping("/api/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> registrar(@RequestBody @Valid User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
} 