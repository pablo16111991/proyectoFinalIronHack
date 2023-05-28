package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenciaRepository extends JpaRepository<Conferencia, Long> {
    public Conferencia findConferenciaByName(String eventName);
}
