package br.com.fiap.date_control_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.date_control_api.model.User;
import br.com.fiap.date_control_api.service.TokenService;

@RestController
public class AuthController {

    public record Token(String token, String email){}
    public record Credentials(String email, String password){}
    
    @Autowired TokenService tokenService;
    @Autowired AuthenticationManager authManager;
    
    @PostMapping("/api/login")
    public Token login(@RequestBody Credentials credentials) {
        var auth = new UsernamePasswordAuthenticationToken(
            credentials.email(), credentials.password()
        );
        var user = (User) authManager.authenticate(auth).getPrincipal();
        return new Token(tokenService.generateToken(user), credentials.email());
    }
} 