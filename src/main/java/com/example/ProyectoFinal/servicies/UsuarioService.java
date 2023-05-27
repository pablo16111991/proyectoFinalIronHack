package com.example.ProyectoFinal.servicies;

import com.example.ProyectoFinal.dto.UsuarioDTO;
import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioService {

    @Autowired
    ParticipanteRepository participanteRepository;

    @Autowired
    OrganizadorRepository organizadorRepository;

    @Autowired
    ConciertoRepository conciertoRepository;
    @Autowired
    ConferenciaRepository conferenciaRepository;
    @Autowired
    TallerRepository tallerRepository;

    public Usuario registrarUsuario(UsuarioDTO usuarioDto) {
        if (usuarioDto.getRole().equals("participante")) {
            Participante participante = new Participante();
            participante.setName(usuarioDto.getName());
            participante.setEmail(usuarioDto.getEmail());
            participante.setPassword(usuarioDto.getPassword());
            return participanteRepository.save(participante);
        } else if (usuarioDto.getRole().equals("organizador")) {
            Organizador organizador = new Organizador();
            organizador.setName(usuarioDto.getName());
            organizador.setEmail(usuarioDto.getEmail());
            organizador.setPassword(usuarioDto.getPassword());
            return organizadorRepository.save(organizador);
        } else {
            throw new IllegalArgumentException("Rol inválido " + usuarioDto.getRole());
        }
    }

    public List<Participante> showAllParticipants() {
        return participanteRepository.findAll();
    }

    public List<Organizador> showAllOrganizers() {
        return organizadorRepository.findAll();
    }

    public Participante inscribirseEvento(Long userId, Long idEvento) {
        Participante participante = participanteRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Participante no encontrado"));
        if (conferenciaRepository.existsById(idEvento)) {
            Conferencia conferencia = conferenciaRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Conferencia no encontrada"));
            conferencia.getParticipantesAsistentes().add(participante);
            participante.getEventosAsistidos().add(conferencia);
            conferenciaRepository.save(conferencia);
        } else if (tallerRepository.existsById(idEvento)) {
            Taller taller = tallerRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Taller no encontrado"));
            taller.getParticipantesAsistentes().add(participante);
            participante.getEventosAsistidos().add(taller);
            tallerRepository.save(taller);
        } else if (conciertoRepository.existsById(idEvento)) {
           Concierto concierto = conciertoRepository.findById(idEvento).orElseThrow(() -> new NoSuchElementException("Concierto no encontrado"));
            concierto.getParticipantesAsistentes().add(participante);
            participante.getEventosAsistidos().add(concierto);
            conciertoRepository.save(concierto);
        } else {
            throw new NoSuchElementException("No existe el evento");
        }
        return participanteRepository.save(participante);
    }

    public List<Event> showAllEventsByParticipant (Long userId) {
       return participanteRepository.findById(userId).get().getEventosAsistidos();
    }

    public void eliminarParticipante (Long userdId, Long eventId) {
        Participante participante = participanteRepository.findById(userdId).orElseThrow(() -> new NoSuchElementException("Participante no encontrado"));

        if (conferenciaRepository.existsById(eventId)) {
            Conferencia conferencia = conferenciaRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Conferencia no encontrada"));
            participante.getEventosAsistidos().remove(conferencia);
            conferencia.getParticipantesAsistentes().remove(participante); // también elimina al participante del evento
            conferenciaRepository.save(conferencia);
        } else if (conciertoRepository.existsById(eventId)) {
            Concierto concierto = conciertoRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Concierto no encontrado"));
            participante.getEventosAsistidos().remove(concierto);
            concierto.getParticipantesAsistentes().remove(participante);
            conciertoRepository.save(concierto);
        } else if (tallerRepository.existsById(eventId)) {
            Taller taller = tallerRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Taller no encontrado"));
            participante.getEventosAsistidos().remove(taller);
            taller.getParticipantesAsistentes().remove(participante);
            tallerRepository.save(taller);
        } else {
            throw new NoSuchElementException("No existe el evento");
        }
        participanteRepository.save(participante);
    }
}
