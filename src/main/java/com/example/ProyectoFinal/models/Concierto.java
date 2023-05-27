package com.example.ProyectoFinal.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concierto extends Event {
    private String artista;
    private String generoMusical;
}
