package com.holdme.holdmeapi_restfull.service;


import com.holdme.holdmeapi_restfull.dto.EventProgrammingCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.EventProgrammingDTO;
import com.holdme.holdmeapi_restfull.dto.UserEventProgrammingDTO;

import java.util.List;

public interface EventProgrammingService {

    EventProgrammingDTO create(EventProgrammingCreateUpdateDTO dto);
    EventProgrammingDTO update(EventProgrammingCreateUpdateDTO dto, int eventsId, int schedulesId);
    void delete(int eventsId, int schedulesId);
    List<EventProgrammingDTO> findByIdEvent(int eventsId);
    List<EventProgrammingDTO> findByIdSchedule(int schedulesId);
    List<EventProgrammingDTO> findAll();
    List<UserEventProgrammingDTO> getUserEventProgramming();
}
