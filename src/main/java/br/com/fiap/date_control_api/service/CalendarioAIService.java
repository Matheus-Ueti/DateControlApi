package br.com.fiap.date_control_api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import br.com.fiap.date_control_api.repository.CalendarioRepository;

@Service
public class CalendarioAIService {

    @Autowired
    CalendarioRepository repository;

    @Value("${sistema.resources}")
    Resource systemResource;

    public String analisar(String username) {
        var eventos = repository.findAll();
        
        if (eventos.isEmpty()) {
            return "Não há eventos para analisar. Adicione alguns eventos ao seu calendário!";
        }

        try (var reader = new BufferedReader(new InputStreamReader(systemResource.getInputStream()))) {
            var categorias = eventos.stream()
                    .collect(Collectors.groupingBy(e -> e.getCategoria()))
                    .entrySet().stream()
                    .sorted((a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
                    .limit(3)
                    .map(e -> e.getKey().toString())
                    .collect(Collectors.joining(", "));
            
            var recentes = eventos.stream()
                    .filter(e -> e.getDataHora().toLocalDate().isAfter(LocalDate.now().minusDays(7)))
                    .count();

            return String.format("""
                    Análise do seu calendário:
                    
                    Você tem um total de %d eventos em seu calendário. Suas categorias mais frequentes são %s.
                    Na última semana, você adicionou %d novos eventos.
                    
                    Recomendo organizar seus eventos por prioridade e agrupar atividades similares para otimizar seu tempo.
                    Tente reservar blocos específicos para tarefas relacionadas e lembre-se de incluir intervalos para descanso.
                    """, 
                    eventos.size(), 
                    categorias, 
                    recentes);
            
        } catch (IOException e) {
            return "Não foi possível gerar a análise. Tente novamente mais tarde.";
        }
    }
} 