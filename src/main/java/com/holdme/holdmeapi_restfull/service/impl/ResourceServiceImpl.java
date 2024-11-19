package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.ResourceCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.ResourceDetailsDTO;
import com.holdme.holdmeapi_restfull.exception.BadRequestException;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.mapper.ResourceMapper;
import com.holdme.holdmeapi_restfull.model.entity.*;
import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import com.holdme.holdmeapi_restfull.repository.ResourceRepository;
import com.holdme.holdmeapi_restfull.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final FileSystemStorageService fileSystemStorageService;

    @Transactional(readOnly = true)
    @Override
    public List<ResourceDetailsDTO> findAll() {
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                .map(resourceMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ResourceDetailsDTO findById(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));

        return resourceMapper.toDetailsDTO(resource);
    }

    @Transactional
    @Override
    public ResourceDetailsDTO create(ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        resourceRepository.findByTitle(resourceCreateUpdateDTO.getTitle())
                .ifPresent(event -> {
                    throw new BadRequestException("El recurso ya existe");
                });

        Resource resource = resourceMapper.toEntity(resourceCreateUpdateDTO);

        resource.setCreatedAt(LocalDateTime.now());

        return resourceMapper.toDetailsDTO(resourceRepository.save(resource));
    }

    @Transactional
    @Override
    public ResourceDetailsDTO update(Integer id, ResourceCreateUpdateDTO updateResourceDTO) {
        Resource resourceFromDB = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));

        resourceRepository.findByTitle(updateResourceDTO.getTitle())
                .ifPresent(resource -> {
                    throw new BadRequestException("El recurso ya existe");
                });

        resourceFromDB.setTitle(updateResourceDTO.getTitle());
        resourceFromDB.setDescription(updateResourceDTO.getDescription());
        resourceFromDB.setUpdatedAt(LocalDateTime.now());
        resourceFromDB.setFilePath(updateResourceDTO.getFilePath());
        resourceFromDB.setCategory(updateResourceDTO.getCategory());
        resourceFromDB.setUrl(updateResourceDTO.getUrl());
        resourceFromDB.setType(updateResourceDTO.getType());
        resourceFromDB.setCreatedAt(LocalDateTime.now());

        return resourceMapper.toDetailsDTO(resourceRepository.save(resourceFromDB));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));

        try {
            fileSystemStorageService.delete(resource.getTitle());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo eliminar el archivo", e);
        }

        resourceRepository.delete(resource);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResourceDetailsDTO> getResourcesFiltered(String titulo, ResourceCategory categoria, ResourceType tipo) {
        // Si 'categoria' o 'tipo' no son nulos, asigna el valor correspondiente del enum. De lo contrario, asigna null.
        ResourceCategory categoriaEnum = categoria != null ? categoria : null;
        ResourceType tipoEnum = tipo != null ? tipo : null;

        // Llamada al repositorio pasando los parámetros ya convertidos a enum (si es necesario).
        List<Resource> resources = resourceRepository.getResourcesFiltered(titulo, categoriaEnum, tipoEnum);

        // Mapea los resultados a DTOs y devuélvelos.
        return resources.stream()
                .map(resourceMapper::toDetailsDTO)
                .toList();
    }
}
