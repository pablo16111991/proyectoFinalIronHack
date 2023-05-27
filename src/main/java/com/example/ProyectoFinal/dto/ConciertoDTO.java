package com.example.ProyectoFinal.dto;

import com.example.ProyectoFinal.models.Participante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConciertoDTO {
    private String name;
    private LocalDate eventDate;
    private List<Participante> participantesAsistentes;
    private List<Participante> participantesPendientes;
    private String artista;
    private String generoMusical;
}
