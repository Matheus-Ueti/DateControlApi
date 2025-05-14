package br.com.fiap.date_control_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.date_control_api.model.Category;
import br.com.fiap.date_control_api.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
            var categories =List.of(
                Category.builder().name("Trabalho").icon("Briefcase").build(),
                Category.builder().name("Lazer").icon("Party").build(),
                Category.builder().name("Educação").icon("Book").build(),
                Category.builder().name("Família").icon("Home").build()
            );
            categoryRepository.saveAll(categories);
        
    }

}