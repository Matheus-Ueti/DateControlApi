package br.com.fiap.date_control_api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.date_control_api.model.Category;
import br.com.fiap.date_control_api.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	// Injeção de Dependência
	@Autowired
	private CategoryRepository repository;

	@GetMapping
	public List<Category> index() {
		return repository.findAll();
	}

	@PostMapping
	// @ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Category> create(@RequestBody Category category) {
		log.info("Cadastrando categoria " + category.getName());
		repository.save(category);
		return ResponseEntity.status(201).body(category);
	}

	@GetMapping("{id}")
	public Category get(@PathVariable Long id) {
		log.info("buscando categoria " + id);
		return getCategory(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void destroy(@PathVariable Long id) {
		log.info("Apagando categoria " + id);
		repository.delete(getCategory(id));
	}

	@PutMapping("{id}")
	public Category update(@PathVariable Long id, @RequestBody Category category) {
		log.info("Atualizando categoria " + id + " para " + category);

		category.setId(id);
		return repository.save(category);
	}

	private Category getCategory(Long id) {
		return repository
				.findById(id)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

}
