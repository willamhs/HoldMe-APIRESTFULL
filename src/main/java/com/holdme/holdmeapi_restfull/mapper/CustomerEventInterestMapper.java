package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestDTO;
import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestRequestDTO;
import com.holdme.holdmeapi_restfull.model.entity.CustomerEventInterest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventInterestMapper {

    private final ModelMapper modelMapper;

    public CustomerEventInterestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configuración para mapear los detalles esenciales de `Event` en `customerEventInterestDTO`
        modelMapper.typeMap(CustomerEventInterest.class, CustomerEventInterestDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getCustomer().getId(), CustomerEventInterestDTO::setCustomerId);
            mapper.map(src -> src.getEvent().getId(), CustomerEventInterestDTO::setEventId);
            mapper.map(src -> src.getEvent().getName(), CustomerEventInterestDTO::setEventName);
            mapper.map(src -> src.getEvent().getCreatedAt(), CustomerEventInterestDTO::setEventCreatedAt);
            mapper.map(src -> src.getEvent().getLocation().getLocation(), CustomerEventInterestDTO::setEventLocationName);
            mapper.map(src -> src.getEvent().getPrice().getPrice(), CustomerEventInterestDTO::setEventPriceAmount);
        });
    }

    // Método para convertir de entidad a DTO
    public CustomerEventInterestDTO toDTO(CustomerEventInterest customerEventInterest) {
        return modelMapper.map(customerEventInterest, CustomerEventInterestDTO.class);
    }

    // Método para convertir de DTO de request a entidad
    public CustomerEventInterest toEntity(CustomerEventInterestRequestDTO customerEventInterestRequestDTO) {
        CustomerEventInterest studentEventInterest = new CustomerEventInterest();
        modelMapper.map(customerEventInterestRequestDTO, studentEventInterest);
        return studentEventInterest;
    }
}
