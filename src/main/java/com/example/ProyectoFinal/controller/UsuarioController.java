package com.example.ProyectoFinal.controller;

import com.example.ProyectoFinal.dto.UsuarioDTO;
import com.example.ProyectoFinal.models.Event;
import com.example.ProyectoFinal.models.Organizador;
import com.example.ProyectoFinal.models.Participante;
import com.example.ProyectoFinal.models.Usuario;
import com.example.ProyectoFinal.servicies.UsuarioService;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar-usuario")
    public Usuario registrarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.registrarUsuario(usuarioDTO);
    }

    @GetMapping("/participantes")
    @ResponseStatus(HttpStatus.OK)
    public List<Participante> showAllParticipants(){
        return usuarioService.showAllParticipants();
    }

    @GetMapping("/organizador")
    @ResponseStatus(HttpStatus.OK)
    public List<Organizador> showAllOrganizers(){
        return usuarioService.showAllOrganizers();
    }

    @PostMapping("/inscribirse-evento/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public Participante inscribirseEvento(@PathVariable Long userId, @PathVariable Long eventId){
        return usuarioService.inscribirseEvento(userId, eventId);
    }

    @GetMapping("/eventos-participantes/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> showAllEventsByParticipant (@PathVariable Long userId){
        return usuarioService.showAllEventsByParticipant(userId);
    }

    @DeleteMapping("/eliminar-participante-evento/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void eliminarParticipante(@PathVariable Long userId, @PathVariable Long eventId){
        usuarioService.eliminarParticipante(userId, eventId);
    }
}
