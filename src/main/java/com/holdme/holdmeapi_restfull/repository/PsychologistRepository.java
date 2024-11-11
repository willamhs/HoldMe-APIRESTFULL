package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Psychologists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<Psychologists, Long>{
    // Buscar psicólogos activos (active = true)
    List<Psychologists> findByActiveTrue();

    // Buscar un psicólogo por email (debe ser único)
    Optional<Psychologists> findByEmail(String email);

    // Buscar psicólogos por nombre (primer o segundo nombre) o apellido
    List<Psychologists> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // Buscar psicólogos por especialidad
    List<Psychologists> findBySpecialtyContainingIgnoreCase(String specialty);

    // Buscar psicólogos por nombre de especialidad y que estén activos
    List<Psychologists> findBySpecialtyContainingIgnoreCaseAndActiveTrue(String specialty);
}
