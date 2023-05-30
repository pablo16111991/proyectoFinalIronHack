package com.example.ProyectoFinal.servicies;

import com.example.ProyectoFinal.dto.ConciertoDTO;
import com.example.ProyectoFinal.dto.ConferenciaDTO;
import com.example.ProyectoFinal.dto.EventDateNNameDTO;
import com.example.ProyectoFinal.dto.TallerDTO;
import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.ConciertoRepository;
import com.example.ProyectoFinal.repositories.ConferenciaRepository;
import com.example.ProyectoFinal.repositories.TallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventoService {
    @Autowired
    ConciertoRepository conciertoRepository;
    @Autowired
    ConferenciaRepository conferenciaRepository;
    @Autowired
    TallerRepository tallerRepository;


    public List<Event> showAllEvents() {
        List<Conferencia> conferencias = conferenciaRepository.findAll();
        List<Concierto> conciertos = conciertoRepository.findAll();
        List<Taller> talleres = tallerRepository.findAll();

        List<Event> events = new ArrayList<>();
        events.addAll(conferencias);
        events.addAll(conciertos);
        events.addAll(talleres);

        return events;
    }

    public List<Concierto> showAllConcerts() {
        return conciertoRepository.findAll();
    }

    public List<Taller> showAllTallers() {
        return tallerRepository.findAll();
    }

    public List<Conferencia> showAllConferences() {
        return conferenciaRepository.findAll();
    }

    public Event findEventById(Long id) {
        if (conferenciaRepository.existsById(id)) {
            return conferenciaRepository.findById(id).get();
        } else if (conciertoRepository.existsById(id)) {
            return conciertoRepository.findById(id).get();
        } else if (tallerRepository.existsById(id)) {
            return tallerRepository.findById(id).get();
        } else {
            throw new NoSuchElementException("No existe ningún evento con ese ID: " + id);
        }
    }

    public void deleteEvent (Long id) {
        if (conferenciaRepository.existsById(id)) {
            conferenciaRepository.deleteById(id);
        } else if (conciertoRepository.existsById(id)) {
            conciertoRepository.deleteById(id);
        } else if (tallerRepository.existsById(id)) {
            tallerRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("No existe ningón evento con ese ID: " + id);
        }
    }

    public Event updateEvento(EventDateNNameDTO eventDateNNameDTO) {
        if (conferenciaRepository.existsById(eventDateNNameDTO.getEventId())) {
            Conferencia conferencia = conferenciaRepository.findById(eventDateNNameDTO.getEventId()).get();
            conferencia.setName(eventDateNNameDTO.getName());
            conferencia.setEventDate(eventDateNNameDTO.getEventDate());
            return conferenciaRepository.save(conferencia);
        } else if (conciertoRepository.existsById((eventDateNNameDTO.getEventId()))) {
            Concierto concierto = conciertoRepository.findById(eventDateNNameDTO.getEventId()).get();
            concierto.setName(eventDateNNameDTO.getName());
            concierto.setEventDate(eventDateNNameDTO.getEventDate());
            return conciertoRepository.save(concierto);
        } else if (tallerRepository.existsById(eventDateNNameDTO.getEventId())) {
            Taller taller = tallerRepository.findById(eventDateNNameDTO.getEventId()).get();
            taller.setName(eventDateNNameDTO.getName());
            taller.setEventDate(eventDateNNameDTO.getEventDate());
            return tallerRepository.save(taller);
        } else {
            throw new NoSuchElementException("No existe ningun evento con ese id " + eventDateNNameDTO.getEventId());
        }

    }


    public Concierto addConcierto(ConciertoDTO conciertoDTO) {
        Concierto concierto = new Concierto();

        concierto.setName(conciertoDTO.getName());
        concierto.setEventDate(conciertoDTO.getEventDate());
        concierto.setArtista(conciertoDTO.getArtista());
        concierto.setGeneroMusical(conciertoDTO.getGeneroMusical());
        List<Participante> participantesAsistentes = new ArrayList<>(conciertoDTO.getParticipantesAsistentes());
        concierto.setParticipantesAsistentes(participantesAsistentes);
        List<Participante> participantesPendientes = new ArrayList<>(conciertoDTO.getParticipantesPendientes());
        concierto.setParticipantesPendientes(participantesPendientes);

        return conciertoRepository.save(concierto);
    }

    public Taller addTaller (TallerDTO tallerDTO) {

        Taller taller = new Taller();

        taller.setName(tallerDTO.getName());
        taller.setEventDate(tallerDTO.getEventDate());
        List<Participante> participantesAsistentes = new ArrayList<>(tallerDTO.getParticipantesAsistentes());
        taller.setParticipantesAsistentes(participantesAsistentes);
        List<Participante> participantesPendientes = new ArrayList<>(tallerDTO.getParticipantesPendientes());
        taller.setParticipantesPendientes(participantesPendientes);
        taller.setInstructor(taller.getInstructor());
        List<String> materiales = new ArrayList<>(tallerDTO.getMateriales());
        taller.setMateriales(materiales);

        return tallerRepository.save(taller);
    }

    public Conferencia addConferencia (ConferenciaDTO conferenciaDTO) {
        Conferencia conferencia = new Conferencia();

        conferencia.setName(conferenciaDTO.getName());
        conferencia.setEventDate(conferenciaDTO.getEventDate());
        List<Participante> participantesAsistentes = new ArrayList<>(conferenciaDTO.getParticipantesAsistentes());
        conferencia.setParticipantesAsistentes(participantesAsistentes);
        List<Participante> participantesPendientes = new ArrayList<>(conferenciaDTO.getParticipantesPendientes());
        conferencia.setParticipantesPendientes(participantesPendientes);
        List<String> ponentes = new ArrayList<>(conferenciaDTO.getPonentes());
        conferencia.setPonentes(ponentes);

        return conferenciaRepository.save(conferencia);

    }

}