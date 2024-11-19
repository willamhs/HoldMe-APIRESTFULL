package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.CommentDTO;
import com.holdme.holdmeapi_restfull.dto.ResourceCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.ResourceDetailsDTO;
import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import com.holdme.holdmeapi_restfull.service.ResourceService;
import com.holdme.holdmeapi_restfull.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final CommentServiceImpl commentService;

    // Obtener todos los recursos
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<ResourceDetailsDTO>> getAllResources() {
        List<ResourceDetailsDTO> resources = resourceService.findAll();
        return ResponseEntity.ok(resources);
    }

    // Obtener un recurso por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResourceDetailsDTO> getResourceById(@PathVariable Integer id) {
        ResourceDetailsDTO resourceDetailsDTO = resourceService.findById(id);
        return ResponseEntity.ok(resourceDetailsDTO);
    }

    // Crear un nuevo recurso
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResourceDetailsDTO> createResource(@Valid @RequestBody ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        ResourceDetailsDTO createdResource = resourceService.create(resourceCreateUpdateDTO);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    // Actualizar un recurso existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResourceDetailsDTO> updateResource(@PathVariable Integer id, @Valid @RequestBody ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        ResourceDetailsDTO updatedResource = resourceService.update(id, resourceCreateUpdateDTO);
        return ResponseEntity.ok(updatedResource);
    }

    // Eliminar un recurso
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteResource(@PathVariable Integer id) {
        resourceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Filtrar recursos por título, categoría y tipo
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<ResourceDetailsDTO>> getResourcesFiltered(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) ResourceCategory category,
            @RequestParam(required = false) ResourceType type) {
        List<ResourceDetailsDTO> filteredResources = resourceService.getResourcesFiltered(title, category, type);
        return ResponseEntity.ok(filteredResources);
    }

    //Estas endpoints son para los comentarios

    // Agregar un comentario a un recurso
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{resourceId}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Integer resourceId,
            @RequestParam Integer userId, // ID del usuario
            @RequestParam String content) { // Contenido del comentario
        CommentDTO commentDTO = commentService.addComment(resourceId, userId, content);
        return ResponseEntity.ok(commentDTO);
    }

    // Obtener comentarios de un recurso
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{resourceId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Integer resourceId) {
        List<CommentDTO> comments = commentService.getCommentsByResource(resourceId);
        return ResponseEntity.ok(comments);
    }
}
