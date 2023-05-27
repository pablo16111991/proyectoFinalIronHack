package com.example.ProyectoFinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
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
public class Organizador extends Usuario {


    @OneToMany (mappedBy = "organizador")
    @JsonBackReference (value = "eventosCreados")
    private List<Event> eventosCreados = new ArrayList<>();



}
