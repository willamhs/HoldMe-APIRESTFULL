package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;
import com.holdme.holdmeapi_restfull.exception.BadRequestException;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.mapper.CustomerMapper;
import com.holdme.holdmeapi_restfull.model.entity.Customer;
import com.holdme.holdmeapi_restfull.repository.CustomerRepository;
import com.holdme.holdmeapi_restfull.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public UserRegistrationDTO create(UserRegistrationDTO userRegistrationDTO) {
        customerRepository.findByFirstNameAndLastName(userRegistrationDTO.getFirstName(), userRegistrationDTO.getLastName())
                .ifPresent(existingStudent ->{
                    throw new BadRequestException("El estudiante ya existe con el mismo nombre y apellido");
                });

        Customer student = customerMapper.toEntity(userRegistrationDTO);
        student.setCreatedAt(LocalDateTime.now());
        student = customerRepository.save(student);
        return customerMapper.toDTO(student);

    }

    //Busqueda por id del estudiante
    @Transactional
    @Override
    public UserProfileDTO findById(Integer id) {
        Customer student = customerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El estudiante con ID "+id+" no fue encontrado"));
        return customerMapper.toDTOs(student);
    }

    @Transactional
    @Override
    public UserRegistrationDTO update(Integer id, UserRegistrationDTO userRegistrationDTO) {
        Customer studentFromDB = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El estudiante con ID " + id + " no fue encontrado"));


        customerRepository.findByFirstNameAndLastName(userRegistrationDTO.getFirstName(), userRegistrationDTO.getLastName())
                .filter(existingAuthor -> !existingAuthor.getId().equals(id))
                .ifPresent(existingAuthor -> {
                    throw new BadRequestException("Ya existe un estudiante con el mismo nombre y apellido");
                });

        // Actualizar los campos
        studentFromDB.setFirstName(userRegistrationDTO.getFirstName());
        studentFromDB.setLastName(userRegistrationDTO.getLastName());
        studentFromDB.setUpdatedAt(LocalDateTime.now());
        studentFromDB.setOccupation(userRegistrationDTO.getOccupation());
        studentFromDB.setCreatedAt(LocalDateTime.now());

        studentFromDB = customerRepository.save(studentFromDB);
        return customerMapper.toDTO(studentFromDB);

    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Customer student = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El autor con ID " + id + " no fue encontrado"));
        customerRepository.delete(student);
    }
}
