package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.ResourceDTO;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.service.impl.FileSystemStorageService;
import com.holdme.holdmeapi_restfull.service.impl.ResourceServiceImpl;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceServiceImpl resourceService;
    private final FileSystemStorageService fileSystemStorageService;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceService, FileSystemStorageService fileSystemStorageService) {
        this.resourceService = resourceService;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    // Obtener todos los recursos
    @GetMapping
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }

    // Obtener recurso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    // Crear nuevo recurso
    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestParam(required = false) MultipartFile file, @RequestBody ResourceDTO resourceDTO) {
        // Si se ha proporcionado un archivo, guardarlo
        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            // Guardar el archivo y obtener el nombre de archivo generado
            fileUrl = fileSystemStorageService.store(file); // Usamos el servicio de almacenamiento
        }

        // Si el archivo fue cargado, se establece la URL del archivo en el DTO
        resourceDTO.setUrl(fileUrl);

        // Crear el nuevo recurso usando el DTO
        Resource newResource = resourceService.createResource(resourceDTO);

        // Retornar el recurso recién creado
        return ResponseEntity.status(HttpStatus.CREATED).body(newResource);
    }

    // Actualizar recurso por ID, incluyendo archivo
    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String title,
            @RequestParam String url,
            @RequestParam String type,
            @RequestParam String category,
            @RequestParam String description) {

        // Obtener el recurso existente
        Resource existingResource = resourceService.getResourceById(id);

        // Crear DTO con los parámetros recibidos
        ResourceDTO resourceDTO = new ResourceDTO(title, url, type, category, description);

        // Si se ha proporcionado un nuevo archivo, actualizarlo
        if (file != null && !file.isEmpty()) {
            // Guardar el archivo y obtener la nueva URL
            String fileName = fileSystemStorageService.store(file); // Guardar el archivo
            resourceDTO.setUrl(fileName); // Establecer la URL del archivo
        } else {
            // Mantener la URL actual si no se proporciona un archivo
            resourceDTO.setUrl(existingResource.getUrl());
        }

        // Actualizar el recurso con los datos del DTO
        Resource updatedResource = resourceService.updateResource(id, resourceDTO);
        return ResponseEntity.ok(updatedResource);
    }

    // Eliminar recurso por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }


    // Subir archivo para un recurso
    @PostMapping("/upload")
    public ResponseEntity<Resource> uploadResource(
            @RequestParam("title") String title,
            @RequestParam("url") String url,
            @RequestParam("type") String type,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {

        try {
            // Guardar archivo y obtener ruta
            String filePath = fileSystemStorageService.store(file);

            // Crear recurso
            ResourceDTO resourceDTO = new ResourceDTO(title, url, type, category, description);
            Resource newResource = resourceService.createResource(resourceDTO);

            // Asignar ruta del archivo y guardar
            newResource.setFilePath(filePath);
            resourceService.save(newResource);

            return ResponseEntity.ok(newResource);
        } catch (IOException e) {
            // Loguea el error para depuración
            e.printStackTrace();

            // Retornar una respuesta con el mensaje de error
            return ResponseEntity.status(500).body(null);
        }
    }


}
