package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestDTO;
import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestRequestDTO;

import java.util.List;

public interface CustomerEventInterestService {
    CustomerEventInterestDTO create(CustomerEventInterestRequestDTO customerEventInterestRequestDTO);
    List<CustomerEventInterestDTO> findByCustomerId();
    List<CustomerEventInterestDTO> findByEventId(Integer eventId);
    void deleteByEventId(Integer eventId);
    boolean isEventFavorite(Integer eventId);
}
