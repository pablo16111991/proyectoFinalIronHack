package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Concierto;
import com.example.ProyectoFinal.models.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenciaRepository extends JpaRepository<Conferencia, Long> {
    public Conferencia findConferenciaByName(String eventName);
    public Conferencia findConferenciaByEventId(Long eventId);
    public Conferencia findConferenciaByEventDate (String date);
}
