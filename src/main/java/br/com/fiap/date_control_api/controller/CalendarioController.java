package br.com.fiap.date_control_api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.date_control_api.model.Calendario;
import br.com.fiap.date_control_api.repository.CalendarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/calendario")
public class CalendarioController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CalendarioRepository repository;

    @GetMapping
    @Cacheable("eventos")
    @Operation(tags = "evento", summary = "Listar eventos", description = "Devolve a lista de eventos do calendário")
    public List<Calendario> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @CacheEvict(value = "eventos", allEntries = true)
    public ResponseEntity<Calendario> create(@RequestBody @Valid Calendario calendario) {
        log.info("Cadastrando compromisso: " + calendario.getDescricao());
        repository.save(calendario);
        return ResponseEntity.status(HttpStatus.CREATED).body(calendario);
    }

    @GetMapping("/{id}")
    @Operation(tags = "evento", summary = "Buscar evento", description = "Busca um evento pelo seu ID")
    public Calendario get(@PathVariable Long id) {
        log.info("Buscando compromisso " + id);
        return getCalendario(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "eventos", allEntries = true)
    @Operation(tags = "evento", summary = "Remover evento", description = "Remove um evento pelo seu ID")
    public void destroy(@PathVariable Long id) {
        log.info("Apagando compromisso " + id);
        repository.delete(getCalendario(id));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "eventos", allEntries = true)
    @Operation(tags = "evento", summary = "Atualizar evento", description = "Atualiza os dados de um evento existente")
    public Calendario update(@PathVariable Long id, @RequestBody @Valid Calendario calendario) {
        log.info("Atualizando compromisso " + id);
        
        calendario.setId(id);
        return repository.save(calendario);
    }

    private Calendario getCalendario(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compromisso não encontrado"));
    }
}
