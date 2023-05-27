package com.example.ProyectoFinal.servicies;

import com.example.ProyectoFinal.dto.CommentRequest;
import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
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
    @Autowired
    CommentRepository commentRepository;

    public Comment addComment(CommentRequest request){

        Comment comment = new Comment();
        comment.setComment(request.getCommentText());
        comment.setCommentDate(LocalDate.now());

        Participante participante = participanteRepository.findById(request.getUserId()).orElseThrow(() -> new NoSuchElementException("Participante no encontrado"));
        comment.setParticipante(participante);
        participante.getComments().add(comment);

        if (conciertoRepository.existsById(request.getEventId())) {
            Concierto concierto =  conciertoRepository.findById(request.getEventId()).orElseThrow(() -> new NoSuchElementException("Concierto no encontrado"));
            comment.setEvent(concierto);
            concierto.getComments().add(comment);
            conciertoRepository.save(concierto);
        } else if (conferenciaRepository.existsById(request.getEventId())) {
            Conferencia conferencia = conferenciaRepository.findById(request.getEventId()).orElseThrow(() -> new NoSuchElementException("Conferencia no encontrada"));
            comment.setEvent(conferencia);
            conferencia.getComments().add(comment);
            conferenciaRepository.save(conferencia);
        } else if (tallerRepository.existsById(request.getEventId())) {
            Taller taller = tallerRepository.findById(request.getEventId()).orElseThrow(() -> new NoSuchElementException("Taller no encontrado"));
            comment.setEvent(taller);
            taller.getComments().add(comment);
            tallerRepository.save(taller);
        } else {
            throw new NoSuchElementException("No existe el evento");
        }
        participanteRepository.save(participante);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByEvent(Long eventId){
        if (conciertoRepository.existsById(eventId)) {
            Concierto concierto = conciertoRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Concierto no encontrado"));
            return concierto.getComments();
        } else if (conferenciaRepository.existsById(eventId)) {
            Conferencia conferencia = conferenciaRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Conferencia no encontrada"));
            return conferencia.getComments();
        } else if (tallerRepository.existsById(eventId)) {
            Taller taller = tallerRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Taller no encontrado"));
            return taller.getComments();
        } else {
            throw new NoSuchElementException("No existe el evento");
        }
    }

    public List<Comment> getCommentsByUserId(Long userId){
        Participante participante = participanteRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Participante no encontrado"));
        if (participante.getComments().isEmpty())
            throw new NoSuchElementException("No hay comentarios para este usuario");
        else
            //return participante.getComments();
            return participante.getComments();

    }

    public Comment deleteComment(Long commentId) {


        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comentario no encontrado"));
        commentRepository.delete(comment);

        Participante participante = comment.getParticipante();
        if (participante == null) {
            throw new NoSuchElementException("No existe el participante");
        }
        participante.getComments().remove(comment);
        participanteRepository.save(participante);

        if (comment.getEvent() instanceof Concierto) {
            Concierto concierto = (Concierto) comment.getEvent();
            concierto.getComments().remove(comment);
            conciertoRepository.save(concierto);
        } else if (comment.getEvent() instanceof Conferencia) {
            Conferencia conferencia = (Conferencia) comment.getEvent();
            conferencia.getComments().remove(comment);
            conferenciaRepository.save(conferencia);
        } else if (comment.getEvent() instanceof Taller) {
            Taller taller = (Taller) comment.getEvent();
            taller.getComments().remove(comment);
            tallerRepository.save(taller);
        } else {

            throw new NoSuchElementException("No existe el evento");
        }

        return comment;

    }
}
