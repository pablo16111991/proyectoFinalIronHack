package com.example.ProyectoFinal.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "participante", fetch = FetchType.EAGER)
    @JsonManagedReference (value = "participante-comment")
    private List<Comment> comments = new ArrayList<>();
    // otros campos, constructores, getters y setters


    @ManyToMany (mappedBy = "participantesAsistentes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference (value = "eventosAsistidos")
    private List<Event> eventosAsistidos = new ArrayList<>();

    @ManyToMany (mappedBy = "participantesPendientes",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference (value = "eventosPendientes")
    private List<Event> eventosPendientes = new ArrayList<>();
}