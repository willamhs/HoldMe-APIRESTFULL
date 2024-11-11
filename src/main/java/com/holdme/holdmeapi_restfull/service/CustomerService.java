package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;

public interface CustomerService {
   UserRegistrationDTO create(UserRegistrationDTO customerDTO);
   UserProfileDTO findById(Integer id);
   UserRegistrationDTO update(Integer id, UserRegistrationDTO updateCustomerDTO);
   void delete(Integer id);
}
