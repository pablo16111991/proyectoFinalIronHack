package com.example.ProyectoFinal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDateNNameDTO {
    @NotNull(message = "El id del evento no puede ser nulo")
    private Long eventId;
    private String name;
    private LocalDate eventDate;
}
