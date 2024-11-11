package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.EventProgrammingCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.EventProgrammingDTO;
import com.holdme.holdmeapi_restfull.model.entity.EventProgramming;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class EventProgrammingMapper {

    private final ModelMapper modelMapper;

    public EventProgrammingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(EventProgramming.class, EventProgrammingDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getEvent().getId(), EventProgrammingDTO::setEventId);
            mapper.map(src -> src.getSchedule().getId(), EventProgrammingDTO::setScheduleId);
            mapper.map(src -> src.getEvent().getName(), EventProgrammingDTO::setEventName);
            mapper.map(src -> src.getEvent().getDescription(), EventProgrammingDTO::setEventDescription);
            mapper.map(src -> src.getSchedule().getStartHour(), EventProgrammingDTO::setScheduleStartHour);
            mapper.map(src -> src.getSchedule().getEndHour(), EventProgrammingDTO::setScheduleEndHour);
            mapper.map(src -> src.getSchedule().getDescription(), EventProgrammingDTO::setScheduleDescription);
        });
    }

    public EventProgrammingDTO toDTO(EventProgramming eventProgramming) {
        EventProgrammingDTO dto = modelMapper.map(eventProgramming, EventProgrammingDTO.class);
        dto.setEventId(eventProgramming.getEvent().getId());
        dto.setScheduleId(eventProgramming.getSchedule().getId());
        return dto;
    }

    public EventProgramming toEntity(EventProgrammingCreateUpdateDTO dto) {
        return modelMapper.map(dto, EventProgramming.class);
    }
}
