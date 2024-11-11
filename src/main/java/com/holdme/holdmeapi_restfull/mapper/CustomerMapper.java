package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;
import com.holdme.holdmeapi_restfull.model.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRegistrationDTO toDTO(Customer customer) {
        return modelMapper.map(customer, UserRegistrationDTO.class);
    }

    public Customer toEntity(UserRegistrationDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public UserProfileDTO toDTOs(Customer customer) {
        return modelMapper.map(customer, UserProfileDTO.class);
    }
}
