package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.CommentDTO;
import com.holdme.holdmeapi_restfull.model.entity.Comment;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.model.entity.User;
import com.holdme.holdmeapi_restfull.repository.CommentRepository;
import com.holdme.holdmeapi_restfull.repository.ResourceRepository;
import com.holdme.holdmeapi_restfull.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public CommentDTO addComment(Integer resourceId, Integer userId, String content) {
        // Buscar recurso
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        // Buscar usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar comentario
        Comment comment = new Comment();
        comment.setResource(resource);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return toDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByResource(Integer resourceId) {
        return commentRepository.findByResourceId(resourceId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getCustomer().getFirstName()); // Asumiendo que Usuario tiene un campo username
        dto.setResourceId(comment.getResource().getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
