package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.EventCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.EventDetailsDTO;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public EventDetailsDTO toDetailsDTO(Event event) {
        EventDetailsDTO eventDetailsDTO = modelMapper.map(event, EventDetailsDTO.class);

        eventDetailsDTO.setCategoryName(event.getCategory().getName());
        eventDetailsDTO.setLocationName(event.getLocation().getLocation());
        eventDetailsDTO.setCityName(event.getLocation().getCity());
        eventDetailsDTO.setPriceValue(event.getPrice().getPrice());
        eventDetailsDTO.setCreatedAt(event.getCreatedAt().toString());

        return eventDetailsDTO;
    }

    public Event toEntity(EventCreateUpdateDTO eventCreateUpdateDTO) {
        return modelMapper.map(eventCreateUpdateDTO, Event.class);
    }

    public EventCreateUpdateDTO toCreateUpdateDTO(Event event) {
        return modelMapper.map(event, EventCreateUpdateDTO.class);
    }
}
