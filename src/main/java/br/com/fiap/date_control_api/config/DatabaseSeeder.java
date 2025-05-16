package br.com.fiap.date_control_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.date_control_api.model.Category;
import br.com.fiap.date_control_api.model.User;
import br.com.fiap.date_control_api.model.UserRole;
import br.com.fiap.date_control_api.repository.CategoryRepository;
import br.com.fiap.date_control_api.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        categoryRepository.saveAll(List.of(
            Category.builder().name("Trabalho").icon("Briefcase").build(),
            Category.builder().name("Reunião").icon("People").build(),
            Category.builder().name("Estudo").icon("Book").build(),
            Category.builder().name("Pessoal").icon("Person").build(),
            Category.builder().name("Saúde").icon("Heart").build(),
            Category.builder().name("Lazer").icon("Party").build(),
            Category.builder().name("Família").icon("Home").build()
        ));
        
        userRepository.saveAll(List.of(
            User.builder()
                .email("admin@fiap.com.br")
                .password(passwordEncoder.encode("123456"))
                .role(UserRole.ADMIN)
                .build(),
            User.builder()
                .email("user@fiap.com.br")
                .password(passwordEncoder.encode("123456"))
                .role(UserRole.USER)
                .build()
        ));
    }
}