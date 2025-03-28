package br.com.fiap.date_control_api.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.date_control_api.model.Calendario;
import br.com.fiap.date_control_api.repository.CalendarioRepository;

@RestController
@RequestMapping("/calendario")
public class CalendarioController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CalendarioRepository repository;

    @GetMapping
    public List<Calendario> index() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Calendario> create(@RequestBody Calendario calendario) {
        log.info("Cadastrando compromisso: " + calendario.getDescricao());
        repository.save(calendario);
        return ResponseEntity.status(HttpStatus.CREATED).body(calendario);
    }

    @GetMapping("/{id}")
    public Calendario get(@PathVariable Long id) {
        log.info("Buscando compromisso " + id);
        return getCalendario(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando compromisso " + id);
        repository.delete(getCalendario(id));
    }

    @PutMapping("/{id}")
    public Calendario update(@PathVariable Long id, @RequestBody Calendario calendario) {
        log.info("Atualizando compromisso " + id);
        
        calendario.setId(id);
        return repository.save(calendario);
    }

    private Calendario getCalendario(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
