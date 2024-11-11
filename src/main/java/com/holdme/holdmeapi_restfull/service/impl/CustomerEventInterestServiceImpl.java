package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestDTO;
import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestRequestDTO;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.mapper.CustomerEventInterestMapper;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.CustomerEventInterest;
import com.holdme.holdmeapi_restfull.model.entity.User;
import com.holdme.holdmeapi_restfull.repository.EventRepository;
import com.holdme.holdmeapi_restfull.repository.CustomerEventInterestRepository;
import com.holdme.holdmeapi_restfull.repository.UserRepository;
import com.holdme.holdmeapi_restfull.service.CustomerEventInterestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerEventInterestServiceImpl implements CustomerEventInterestService {

    private final CustomerEventInterestRepository customerEventInterestRepository;
    private final CustomerEventInterestMapper customerEventInterestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CustomerEventInterestServiceImpl(
            CustomerEventInterestRepository customerEventInterestRepository,
            CustomerEventInterestMapper customerEventInterestMapper,
            UserRepository userRepository,
            EventRepository eventRepository) {
        this.customerEventInterestRepository = customerEventInterestRepository;
        this.customerEventInterestMapper = customerEventInterestMapper;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public CustomerEventInterestDTO create(CustomerEventInterestRequestDTO studentEventInterestRequestDTO) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Integer studentId = user.getCustomer().getId();
        Integer eventId = studentEventInterestRequestDTO.getEventId();

        // Validar que el evento exista
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        // Verificar si el interés ya existe para evitar duplicados
        boolean exists = customerEventInterestRepository.existsByCustomerIdAndEventId(studentId, eventId);
        if (exists) {
            throw new IllegalStateException("Ya está registrado en este evento.");
        }

        // Mapear el DTO a la entidad
        CustomerEventInterest customerEventInterest = customerEventInterestMapper.toEntity(studentEventInterestRequestDTO);

        // Asignar el evento y el estudiante autenticado
        customerEventInterest.setEvent(event);
        customerEventInterest.setCustomer(user.getCustomer());

        // Guardar en el repositorio
        CustomerEventInterest savedStudentEventInterest = customerEventInterestRepository.save(customerEventInterest);

        // Retornar el DTO completo
        return customerEventInterestMapper.toDTO(savedStudentEventInterest);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerEventInterestDTO> findByCustomerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Integer studentAuthenticatedUserId = user.getCustomer().getId();

        return customerEventInterestRepository.findAll().stream()
                .filter(interest -> interest.getCustomer().getId().equals(studentAuthenticatedUserId))
                .map(customerEventInterestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerEventInterestDTO> findByEventId(Integer eventId) {
        return customerEventInterestRepository.findAll().stream()
                .filter(interest -> interest.getEvent().getId().equals(eventId))
                .map(customerEventInterestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteByEventId(Integer eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Integer studentId = user.getCustomer().getId();
        customerEventInterestRepository.deleteByCustomerIdAndEventId(studentId, eventId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isEventFavorite(Integer eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Integer customerId = user.getCustomer().getId();

        return customerEventInterestRepository.existsByCustomerIdAndEventId(customerId, eventId);
    }
}
