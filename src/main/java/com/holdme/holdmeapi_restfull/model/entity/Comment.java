package com.holdme.holdmeapi_restfull.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relación con la tabla Usuario

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource; // Relación con la tabla Recurso

    @Column(nullable = false)
    private String content; // Contenido del comentario

    private LocalDateTime createdAt; // Fecha y hora del comentario
}
