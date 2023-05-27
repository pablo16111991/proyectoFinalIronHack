package com.example.ProyectoFinal.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Participante extends Usuario {

    @OneToMany(mappedBy = "participante")
    @JsonManagedReference (value = "participante-comment")
    private List<Comment> comments = new ArrayList<>();
    // otros campos, constructores, getters y setters


    @ManyToMany (mappedBy = "participantesAsistentes")
    @JsonBackReference (value = "eventosAsistidos")
    private List<Event> eventosAsistidos = new ArrayList<>();

    @ManyToMany (mappedBy = "participantesPendientes")
    @JsonBackReference (value = "eventosPendientes")
    private List<Event> eventosPendientes = new ArrayList<>();
}