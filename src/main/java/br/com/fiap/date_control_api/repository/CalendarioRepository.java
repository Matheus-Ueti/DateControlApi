package br.com.fiap.date_control_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.date_control_api.model.Calendario;

public interface  CalendarioRepository extends JpaRepository<Calendario, Long> {
    
}
