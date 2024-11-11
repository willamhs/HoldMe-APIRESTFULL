package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.model.entity.Psychologists;
import com.holdme.holdmeapi_restfull.repository.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PsychologistServiceImpl {
    private final PsychologistRepository psychologistRepository;
    private final FileSystemStorageService fileSystemStorageService;

    @Autowired
    public PsychologistServiceImpl(PsychologistRepository psychologistRepository, FileSystemStorageService fileSystemStorageService) {
        this.psychologistRepository = psychologistRepository;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    // Obtener todos los psicólogos activos
    public List<Psychologists> getAllActivePsychologists() {
        return psychologistRepository.findByActiveTrue();
    }

    // Obtener un psicólogo por ID (usando Optional)
    public Optional<Psychologists> getPsychologistById(Long id) {
        return psychologistRepository.findById(id); // Devuelve un Optional
    }


    // Crear un nuevo psicólogo
    public Psychologists createPsychologist(Psychologists psychologist) {
        validatePsychologistData(psychologist);  // Validación antes de guardar
        return psychologistRepository.save(psychologist);
    }

    // Actualizar un psicólogo existente
    @Transactional
    public Psychologists updatePsychologist(Long id, Psychologists psychologistDetails, MultipartFile file) {
        Psychologists existingPsychologist = psychologistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        // Actualizar campos
        existingPsychologist.setFirstName(psychologistDetails.getFirstName());
        existingPsychologist.setLastName(psychologistDetails.getLastName());
        existingPsychologist.setEmail(psychologistDetails.getEmail());
        existingPsychologist.setPhoneNumber(psychologistDetails.getPhoneNumber());
        existingPsychologist.setSpecialty(psychologistDetails.getSpecialty());
        existingPsychologist.setBio(psychologistDetails.getBio());
        existingPsychologist.setAvailableHours(psychologistDetails.getAvailableHours());
        existingPsychologist.setActive(psychologistDetails.isActive());

        // Si hay un archivo, procesarlo
        if (file != null && !file.isEmpty()) {
            String fileName = fileSystemStorageService.store(file);  // Usar fileSystemStorageService para guardar el archivo
            existingPsychologist.setUrl(fileName);  // Aquí deberías tener un campo `url` en la entidad `Psychologists`
        }

        // Guardar el psicólogo actualizado
        return psychologistRepository.save(existingPsychologist);
    }

    // Desactivar un psicólogo (por ejemplo, dado que podría haberse retirado o ya no esté disponible)
    public void deactivatePsychologist(Long id) {
        // Obtener el psicólogo o lanzar excepción si no se encuentra
        Psychologists psychologist = getPsychologistById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found with id " + id));

        psychologist.setActive(false);
        psychologistRepository.save(psychologist);
    }

    // Eliminar un psicólogo
    public void deletePsychologist(Long id) {
        // Obtener el psicólogo o lanzar excepción si no se encuentra
        Psychologists psychologist = getPsychologistById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found with id " + id));

        psychologistRepository.delete(psychologist);
    }

    // Verificar si el correo ya está en uso por otro psicólogo
    public boolean emailExists(String email) {
        return psychologistRepository.findByEmail(email).isPresent();
    }

    // Buscar psicólogos por nombre (primer o segundo nombre) o especialidad
    public List<Psychologists> searchPsychologists(String name, String specialty) {
        if (name != null && !name.isEmpty()) {
            return psychologistRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        } else if (specialty != null && !specialty.isEmpty()) {
            return psychologistRepository.findBySpecialtyContainingIgnoreCase(specialty);
        } else {
            return psychologistRepository.findAll(); // Devuelve todos los psicólogos si no se especifica nombre o especialidad
        }
    }

    // Validar los datos del psicólogo antes de guardar o actualizar
    private void validatePsychologistData(Psychologists psychologist) {
        if (psychologist.getFirstName() == null || psychologist.getFirstName().isEmpty()) {
            throw new InvalidDataAccessApiUsageException("First name cannot be empty");
        }
        if (psychologist.getLastName() == null || psychologist.getLastName().isEmpty()) {
            throw new InvalidDataAccessApiUsageException("Last name cannot be empty");
        }
        if (psychologist.getEmail() == null || !psychologist.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidDataAccessApiUsageException("Invalid email format");
        }
        if (psychologist.getPhoneNumber() == null || psychologist.getPhoneNumber().isEmpty()) {
            throw new InvalidDataAccessApiUsageException("Phone number cannot be empty");
        }
        if (psychologist.getSpecialty() == null || psychologist.getSpecialty().isEmpty()) {
            throw new InvalidDataAccessApiUsageException("Specialty cannot be empty");
        }
    }

    // Obtener todos los psicólogos por especialidad
    public List<Psychologists> getActivePsychologistsBySpecialty(String specialty) {
        return psychologistRepository.findBySpecialtyContainingIgnoreCaseAndActiveTrue(specialty);
    }
}
