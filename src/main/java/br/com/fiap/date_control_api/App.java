package br.com.fiap.date_control_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Date Control API", version = "v1", description = "API do sistema de controle de datas"))
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
