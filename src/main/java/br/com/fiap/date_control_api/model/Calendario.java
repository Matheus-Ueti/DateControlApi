package br.com.fiap.date_control_api.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Calendario {
    
    public enum Categoria {
        EVENTO_IMPORTANTE,
        EVENTO_LAZER,
        EVENTO_CASA,
        EVENTO_VARIADO
    }
    private String descricao;
    private LocalDateTime dataHora;
    private Categoria categoria;
    private boolean concluido;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
}