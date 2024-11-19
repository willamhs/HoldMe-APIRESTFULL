package com.holdme.holdmeapi_restfull.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer id;
    private Integer userId; // ID del usuario que hizo el comentario
    private String username; // Nombre del usuario
    private Integer resourceId; // ID del recurso asociado
    private String content; // Contenido del comentario
    private LocalDateTime createdAt; // Fecha de creación
}
