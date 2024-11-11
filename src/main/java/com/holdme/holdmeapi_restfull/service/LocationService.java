package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.LocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    List<LocationDTO> findAll();
    Page<LocationDTO> paginate(Pageable pageable);
    LocationDTO findById(Integer id);
    LocationDTO create(LocationDTO locationDTO);
    LocationDTO update(Integer id, LocationDTO updatelocationDTO);
    void delete(Integer id);
}
