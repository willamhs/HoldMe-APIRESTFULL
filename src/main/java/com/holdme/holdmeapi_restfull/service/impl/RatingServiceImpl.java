package com.holdme.holdmeapi_restfull.service.impl;


import com.holdme.holdmeapi_restfull.dto.RatingCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.RatingDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.PuntuacionReportDTO;
import com.holdme.holdmeapi_restfull.exception.BadRequestException;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.integration.notification.email.dto.Mail;
import com.holdme.holdmeapi_restfull.integration.notification.email.service.EmailService;
import com.holdme.holdmeapi_restfull.mapper.RatingMapper;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.Rating;
import com.holdme.holdmeapi_restfull.model.entity.User;
import com.holdme.holdmeapi_restfull.repository.EventRepository;
import com.holdme.holdmeapi_restfull.repository.RatingRepository;
import com.holdme.holdmeapi_restfull.repository.CustomerRepository;
import com.holdme.holdmeapi_restfull.repository.UserRepository;
import com.holdme.holdmeapi_restfull.service.RatingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final EventRepository eventRepository;
    private final CustomerRepository studentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Transactional(readOnly = true)
    @Override
    public List<PuntuacionReportDTO> getRateReportByDate() {
        List<Object[]> results = ratingRepository.getRatingReportByDate();
        //Mapeo de la lista de objetos a una lista de PuntuacionDTO
        List<PuntuacionReportDTO> PuntuacionReportDTOS = results.stream()
                .map(result ->
                        new PuntuacionReportDTO (
                                ((Integer) result[0]).intValue(),
                                (String) result[1]
                        )
                ).toList();
        return PuntuacionReportDTOS;
    }

    @Override
    public List<RatingDetailsDTO> findAll() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(ratingMapper::toDetailsDTO)
                .toList();
    }

    @Override
    public RatingDetailsDTO findById(Integer id) {
        Rating rating = ratingRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado con id: " + id));
        return ratingMapper.toDetailsDTO(rating);
    }

    @Transactional
    @Override
    public RatingDetailsDTO create(RatingCreateUpdateDTO ratingCreateUpdateDTO) throws MessagingException {

        ratingRepository.findByEventIdAndUserId(ratingCreateUpdateDTO.getEventId(), ratingCreateUpdateDTO.getUserId())
                .ifPresent(student -> {
                    throw new BadRequestException("Ya haz calificado");
                });

        Event event = eventRepository.findById(ratingCreateUpdateDTO.getEventId())
                .orElseThrow(()->new ResourceNotFoundException("Event not found with id: " + ratingCreateUpdateDTO.getEventId()));

        User user = userRepository.findById(ratingCreateUpdateDTO.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: " + ratingCreateUpdateDTO.getUserId()));

        if (ratingCreateUpdateDTO.getRate() < 1 || ratingCreateUpdateDTO.getRate() > 5) {
            throw new BadRequestException("La puntuacion debe estar entre 1 y 5");
        };

        Rating rate = ratingMapper.toEntity(ratingCreateUpdateDTO);

        rate.setEvent(event);
        rate.setUser(user);
        rate.setCreated_at(LocalDateTime.now());
        rate.setRate(ratingCreateUpdateDTO.getRate());

        sendRatingConfirmationEmail(event.getName());

        return ratingMapper.toDetailsDTO(ratingRepository.save(rate));
    }

    @Transactional
    @Override
    public RatingDetailsDTO update(Integer id, RatingCreateUpdateDTO updateRate) {
        Rating ratingFromDb = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La puntuacion con ID " + id + " no fue encontrado"));

        ratingRepository.findByRate(updateRate.getRate())
                .filter(existingPuntuacion -> !existingPuntuacion.getId().equals(id))
                .ifPresent(existingPuntuacion -> {
                    throw new BadRequestException("La puntuacion ya existe");
                });

        ratingRepository.findByEventIdAndUserId(updateRate.getEventId(), updateRate.getUserId())
                .filter(existingPuntuacion -> !existingPuntuacion.getId().equals(id))
                .ifPresent(existingPuntuacion -> {
                    throw new RuntimeException("La puntuacion ya existe para este evento y estudiante");
                });

        Event event = eventRepository.findById(updateRate.getEventId())
                .orElseThrow(()->new ResourceNotFoundException("Event not found with id: " + updateRate.getEventId()));

        User user = userRepository.findById(updateRate.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: " + updateRate.getUserId()));

        //Actualizar los campos
        ratingFromDb.setEvent(event);
        ratingFromDb.setUser(user);
        ratingFromDb.setUpdated_at(LocalDateTime.now());
        ratingFromDb.setRate(updateRate.getRate());

        return ratingMapper.toDetailsDTO(ratingRepository.save(ratingFromDb));
    }

    @Override
    public void delete(Integer id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calificacion no existente con id: " + id));
        ratingRepository.delete(rating);
    }

    private void sendRatingConfirmationEmail(String eventName) throws MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Map<String, Object> model = new HashMap<>();
        model.put("user", userEmail);
        model.put("eventName", eventName);

        Mail mail = emailService.createMail(
                userEmail,
                "Confirmacion de Publicacion de Comentario",
                model,
                mailFrom
        );
        emailService.sendMail(mail, "email/acknowledgments-template");

    }
}