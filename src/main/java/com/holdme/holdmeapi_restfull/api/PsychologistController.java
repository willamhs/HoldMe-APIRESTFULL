package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.PsychologistDTO;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.model.entity.Psychologists;
import com.holdme.holdmeapi_restfull.service.impl.PsychologistServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/psychologists")
public class PsychologistController {
    private final PsychologistServiceImpl psychologistService;

    @Autowired
    public PsychologistController(PsychologistServiceImpl psychologistService) {
        this.psychologistService = psychologistService;
    }

    // Obtener todos los psicólogos activos
    @GetMapping("/active")
    public ResponseEntity<List<Psychologists>> getAllActivePsychologists() {
        List<Psychologists> psychologists = psychologistService.getAllActivePsychologists();
        return ResponseEntity.ok(psychologists);
    }

    // Obtener psicólogo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Psychologists> getPsychologistById(@PathVariable Long id) {
        Optional<Psychologists> psychologist = psychologistService.getPsychologistById(id);

        if (psychologist.isPresent()) {
            return ResponseEntity.ok(psychologist.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Crear un nuevo psicólogo
    @PostMapping
    public ResponseEntity<Psychologists> createPsychologist(@Valid @RequestBody PsychologistDTO psychologistDTO) {
        // Convertir PsychologistDTO a Psychologists
        Psychologists psychologist = new Psychologists();
        psychologist.setFirstName(psychologistDTO.getFirstName());
        psychologist.setLastName(psychologistDTO.getLastName());
        psychologist.setEmail(psychologistDTO.getEmail());
        psychologist.setPhoneNumber(psychologistDTO.getPhoneNumber());
        psychologist.setSpecialty(psychologistDTO.getSpecialty());
        psychologist.setBio(psychologistDTO.getBio());
        psychologist.setAvailableHours(psychologistDTO.getAvailableHours());
        psychologist.setActive(psychologistDTO.getActive());

        // Guardar el psicólogo en la base de datos
        Psychologists savedPsychologist = psychologistService.createPsychologist(psychologist);
        return ResponseEntity.ok(savedPsychologist);
    }
    // Actualizar un psicólogo
    // Actualizar un psicólogo existente
    @PutMapping("/{id}")
    public ResponseEntity<Psychologists> updatePsychologist(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile file,
            @Valid @RequestBody PsychologistDTO psychologistDTO) {

        // Convertir PsychologistDTO a Psychologists
        Psychologists psychologist = new Psychologists();
        psychologist.setFirstName(psychologistDTO.getFirstName());
        psychologist.setLastName(psychologistDTO.getLastName());
        psychologist.setEmail(psychologistDTO.getEmail());
        psychologist.setPhoneNumber(psychologistDTO.getPhoneNumber());
        psychologist.setSpecialty(psychologistDTO.getSpecialty());
        psychologist.setBio(psychologistDTO.getBio());
        psychologist.setAvailableHours(psychologistDTO.getAvailableHours());
        psychologist.setActive(psychologistDTO.getActive());

        // Llamar al servicio para actualizar el psicólogo
        Psychologists updatedPsychologist = psychologistService.updatePsychologist(id, psychologist, file);
        return ResponseEntity.ok(updatedPsychologist);
    }
    // Desactivar un psicólogo
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivatePsychologist(@PathVariable Long id) {
        psychologistService.deactivatePsychologist(id);
        return ResponseEntity.ok("Psychologist with id " + id + " has been deactivated.");
    }

    // Eliminar un psicólogo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePsychologist(@PathVariable Long id) {
        psychologistService.deletePsychologist(id);
        return ResponseEntity.ok("Psychologist with id " + id + " has been deleted.");
    }

    // Verificar si el correo ya está en uso
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = psychologistService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    // Obtener psicólogos activos por especialidad
    @GetMapping("/active/specialty")
    public ResponseEntity<List<Psychologists>> getActivePsychologistsBySpecialty(@RequestParam String specialty) {
        List<Psychologists> psychologists = psychologistService.getActivePsychologistsBySpecialty(specialty);
        return ResponseEntity.ok(psychologists);
    }

    // Obtener psicólogos activos por nombre o apellido (búsqueda por texto)
    @GetMapping("/search")
    public ResponseEntity<List<Psychologists>> searchPsychologists(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<Psychologists> psychologists = psychologistService.searchPsychologists(firstName, lastName);
        return ResponseEntity.ok(psychologists);
    }
}
