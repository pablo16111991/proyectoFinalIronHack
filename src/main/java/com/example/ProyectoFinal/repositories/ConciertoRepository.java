package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Concierto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConciertoRepository extends JpaRepository<Concierto, Long> {

    public Concierto findConciertoByName (String name);

    public Concierto findConciertoByEventId (Long id);

    public Concierto findConciertoByEventDate (String date);
}
