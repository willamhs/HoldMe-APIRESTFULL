package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAll();
    Page<ScheduleDTO> paginate(Pageable pageable);
    ScheduleDTO findById(Integer id);
    ScheduleDTO create(ScheduleDTO scheduleDTO);
    ScheduleDTO update(Integer id, ScheduleDTO updateScheduleDTO);
    void delete(Integer id);
}
