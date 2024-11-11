package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.ResourceDTO;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
public class ResourceServiceImpl {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    public Resource createResource(ResourceDTO resourceDTO) {
        if (resourceRepository.findByTitle(resourceDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Resource with the same title already exists");
        }

        Resource resource = new Resource();
        resource.setTitle(resourceDTO.getTitle());
        resource.setUrl(resourceDTO.getUrl());
        resource.setType(resourceDTO.getType());
        resource.setCategory(resourceDTO.getCategory());
        resource.setDescription(resourceDTO.getDescription());
        return resourceRepository.save(resource);
    }


    public void deleteResource(Long id) {
        Resource resource = getResourceById(id);
        resourceRepository.delete(resource);
    }

    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, ResourceDTO resourceDTO) {
        // Buscar el recurso existente
        Resource existingResource = resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado"));

        // Actualizar los campos del recurso
        existingResource.setTitle(resourceDTO.getTitle());
        existingResource.setCategory(resourceDTO.getCategory());
        existingResource.setDescription(resourceDTO.getDescription());
        existingResource.setType(resourceDTO.getType());
        existingResource.setUrl(resourceDTO.getUrl());  // Actualiza la URL del archivo (si hay uno nuevo)

        // Guardar el recurso actualizado
        return resourceRepository.save(existingResource);
    }

}
