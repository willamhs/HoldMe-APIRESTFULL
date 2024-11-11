package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.ScheduleDTO;
import com.holdme.holdmeapi_restfull.model.entity.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {
    private final ModelMapper modelMapper;

    public ScheduleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ScheduleDTO toDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    public Schedule toEntity(ScheduleDTO scheduleDTO) {
        return modelMapper.map(scheduleDTO, Schedule.class);
    }
}
