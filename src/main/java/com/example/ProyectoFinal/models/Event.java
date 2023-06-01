package com.example.ProyectoFinal.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private LocalDate eventDate;
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference (value = "eventosAsistidos")
    private List<Participante> participantesAsistentes = new ArrayList<>();
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference (value = "eventosPendientes")
    private List<Participante> participantesPendientes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "organizador_id")
    @JsonBackReference (value = "eventosCreados")
    private Organizador organizador;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @JsonBackReference (value = "event-comment")
    private List<Comment> comments = new ArrayList<>();

}
