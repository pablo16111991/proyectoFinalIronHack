package com.example.ProyectoFinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment;
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn (name = "event_id")
    @JsonBackReference (value = "event-comment")
    private Event event;

    @ManyToOne
    @JoinColumn (name = "participante_id")
    @JsonBackReference (value = "participante-comment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Participante participante;


}
