package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    public Participante findParticipanteByName(String nametag);

    public Participante findParticipanteByEmail(String email);

}
