package br.com.fiap.date_control_api.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.fiap.date_control_api.model.User;
import br.com.fiap.date_control_api.repository.UserRepository;

@Service
public class TokenService {

    @Autowired UserRepository userRepository;
    @Value("${jwt.secret}") String secret;

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer("DateControlApi")
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(secret));
    }

    public User getUserFromToken(String token) {
        try {
            var email = JWT.require(Algorithm.HMAC256(secret))
                        .withIssuer("DateControlApi")
                        .build()
                        .verify(token)
                        .getSubject();
            
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new JWTVerificationException("Usuário não encontrado"));
            
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }
} 