package br.com.fiap.date_control_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendario {
    
    public enum Categoria {
        EVENTO_IMPORTANTE,
        EVENTO_LAZER,
        EVENTO_CASA,
        EVENTO_VARIADO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "A descrição do evento é obrigatória")
    @Size(min = 3, max = 255, message = "Deve ter pelo menos 3 letras")
    private String descricao;
    
    @NotNull(message = "A data e hora do evento são obrigatórias")
    private LocalDateTime dataHora;
    
    @NotNull(message = "A categoria do evento é obrigatória")
    private Categoria categoria;
    
    private boolean concluido;
}