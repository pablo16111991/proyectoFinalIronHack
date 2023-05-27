package com.example.ProyectoFinal.controller;

import com.example.ProyectoFinal.dto.ConciertoDTO;
import com.example.ProyectoFinal.dto.EventDateNNameDTO;
import com.example.ProyectoFinal.models.Concierto;
import com.example.ProyectoFinal.models.Conferencia;
import com.example.ProyectoFinal.models.Event;
import com.example.ProyectoFinal.models.Taller;
import com.example.ProyectoFinal.servicies.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventoService eventoService;

    public EventController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping(value = "/events")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> showAllEvents() {
        return eventoService.showAllEvents();
    }

    @GetMapping(value = "/events-concierto")
    @ResponseStatus(HttpStatus.OK)
    public List<Concierto> showAllConcerts() {
        return eventoService.showAllConcerts();
    }
    @GetMapping(value = "/events-taller")
    @ResponseStatus(HttpStatus.OK)
    public List<Taller> showAllTallers() {
        return eventoService.showAllTallers();
    }
    @GetMapping(value = "/events-conferencia")
    @ResponseStatus(HttpStatus.OK)
    public List<Conferencia> showAllConferences() {
        return eventoService.showAllConferences();
    }

    @GetMapping(value = "/encontrar event/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event findEvent(@PathVariable Long id) {
        return eventoService.findEventById(id);
    }

  /*  @PostMapping (value = "/add-concierto")
    @ResponseStatus(HttpStatus.CREATED)
    public Concierto addConcert (@RequestBody Concierto concierto){
        return eventoService.addConcierto(concierto);
    }

    @PostMapping (value = "/add-taller")
    @ResponseStatus(HttpStatus.CREATED)
    public Taller addTaller (@RequestBody Taller taller){
        return eventoService.addTaller(taller);
    }

    @PostMapping (value = "/add-conferencia")
    @ResponseStatus(HttpStatus.CREATED)
    public Conferencia addConferencia (@RequestBody Conferencia conferencia){
        return eventoService.addConferencia(conferencia);
    }*/

    @DeleteMapping(value = "/borrar-evento/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConcert (@PathVariable Long id){
        eventoService.deleteEvent(id);
    }


    @PatchMapping(value = "/actualizar-evento")
    @ResponseStatus(HttpStatus.OK)
    public Event updateEvent (@RequestBody EventDateNNameDTO eventDateNNameDTO) {
        return eventoService.updateEvento(eventDateNNameDTO);
    }

    @PostMapping (value = "/add-concierto")
    @ResponseStatus(HttpStatus.CREATED)
    public Concierto addConcert (@RequestBody ConciertoDTO conciertoDTO){
        return eventoService.addConcierto(conciertoDTO);
    }


}