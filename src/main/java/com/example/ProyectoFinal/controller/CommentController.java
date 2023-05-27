package com.example.ProyectoFinal.controller;

import com.example.ProyectoFinal.dto.CommentRequest;
import com.example.ProyectoFinal.models.Comment;
import com.example.ProyectoFinal.servicies.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping(value = "/añadir-comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@RequestBody CommentRequest request) {
        return commentService.addComment(request);
    }

    @GetMapping(value = "/comment-by-eventId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Comment> getCommentsByEventId(@PathVariable Long id) {
        return commentService.getCommentsByEvent(id);
    }

    @GetMapping(value = "/comment-by-userId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Comment> getCommentsByUserId(@PathVariable Long id) {
        return commentService.getCommentsByUserId(id);
    }

    @DeleteMapping(value = "/delete-comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
