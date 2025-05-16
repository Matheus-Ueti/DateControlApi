package br.com.fiap.date_control_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PromptConfig {

    @Bean
    Resource systemResource (){
        return new ClassPathResource("/prompts/system.st");
    }
} 