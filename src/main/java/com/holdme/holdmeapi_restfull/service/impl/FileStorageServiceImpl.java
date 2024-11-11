package com.holdme.holdmeapi_restfull.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl {
    //@Value("${file.upload-dir}")
    //private String uploadDir;

    //private final Path fileStorageLocation;

    /*
    @Autowired
    public FileStorageServiceImpl() {
        // Directorio donde se almacenarán los archivos
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear el directorio para subir archivos.", e);
        }
    }



    // Método para guardar el archivo
    public String saveFile(MultipartFile file) {
        // Obtener el nombre del archivo
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Verificar si el nombre del archivo contiene caracteres inválidos
            if (fileName.contains("..")) {
                throw new RuntimeException("El nombre del archivo contiene caracteres no permitidos.");
            }

            // Crear la ruta donde se guardará el archivo
            Path targetLocation = fileStorageLocation.resolve(fileName);

            // Transferir el archivo al sistema de archivos
            file.transferTo(targetLocation);

            // Retornar la URL o ruta del archivo guardado
            return "/uploads/" + fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar el archivo: " + ex.getMessage(), ex);
        }
    }

     */


}
