package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Concierto;
import com.example.ProyectoFinal.models.Taller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TallerRepository extends JpaRepository<Taller, Long> {

    public Taller findTallerByName (String name);

    public Taller findTallerByEventId (Long id);

    public Taller findTallerByEventDate (String date);
}
