package com.example.ProyectoFinal.repositories;

import com.example.ProyectoFinal.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
