package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.EventCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.EventDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.FilteredEventsDTO;
import com.holdme.holdmeapi_restfull.exception.BadRequestException;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.mapper.EventMapper;
import com.holdme.holdmeapi_restfull.model.entity.Category;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.Price;
import com.holdme.holdmeapi_restfull.model.entity.Location;
import com.holdme.holdmeapi_restfull.repository.CategoryRepository;
import com.holdme.holdmeapi_restfull.repository.EventRepository;
import com.holdme.holdmeapi_restfull.repository.PriceRepository;
import com.holdme.holdmeapi_restfull.repository.LocationRepository;
import com.holdme.holdmeapi_restfull.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final PriceRepository priceRepository;
    private final EventMapper eventMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EventDetailsDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public EventDetailsDTO findById(Integer id) {

        Event event = eventRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));

        return eventMapper.toDetailsDTO(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FilteredEventsDTO> getEventsFiltered(Float precioMin, Float precioMax, String categoriaName, String ubicacion) {
        List<Object[]> results = eventRepository.getEventsFiltered(precioMin, precioMax, categoriaName, ubicacion);
        //Mapeo de la lista de objetos a una lista de FilteredEventsDTO
        List<FilteredEventsDTO> filteredEventsDTOS = results.stream()
                .map(result ->
                        new FilteredEventsDTO(
                                ((Integer) result[0]).intValue(),  // id (event_id)
                                (String) result[1],                // name (event_name)
                                (String) result[2],                // description (event_description)
                                (Integer) result[3],               // capacity (event_capacity)
                                (String) result[4],                // locationName (location_name)
                                (String) result[5],                // categoryName (category_name)
                                (Float) result[6]             // PriceValue (event_price)
                        )
                ).toList();

        return filteredEventsDTOS;
    }

    @Transactional
    @Override
    public EventDetailsDTO create(EventCreateUpdateDTO eventCreateUpdateDTO) {

        eventRepository.findByNameAndDescription(eventCreateUpdateDTO.getName(), eventCreateUpdateDTO.getDescription())
                .ifPresent(event -> {
                    throw new BadRequestException("El evento ya existe");
                });

        // Asigna los atributos necesarios antes de guardar
        Category category = categoryRepository.findById(eventCreateUpdateDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria not found with id: " + eventCreateUpdateDTO.getCategoryId()));

        Location location = locationRepository.findById(eventCreateUpdateDTO.getLocationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ubicacion not found with id: " + eventCreateUpdateDTO.getLocationId()));

        Price price = priceRepository.findById(eventCreateUpdateDTO.getPriceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tarifario not found with id: " + eventCreateUpdateDTO.getPriceId()));

        Event event = eventMapper.toEntity(eventCreateUpdateDTO);

        event.setCategory(category);
        event.setLocation(location);
        event.setPrice(price);
        event.setCreatedAt(LocalDateTime.now());

        return eventMapper.toDetailsDTO(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventDetailsDTO update(Integer id, EventCreateUpdateDTO updateEvent) {

        Event eventFromDB = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento non existente con id" + id));

        eventRepository.findByNameAndDescription(updateEvent.getName(), updateEvent.getDescription())
                .ifPresent(event -> {
                    throw new BadRequestException("El evento ya existe");
                });

        // Asigna los atributos necesarios antes de guardar
        Category category = categoryRepository.findById(updateEvent.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria not found with id: " + updateEvent.getCategoryId()));

        Location location = locationRepository.findById(updateEvent.getLocationId())
                .orElseThrow(() -> new RuntimeException("Ubicacion not found with id: " + updateEvent.getLocationId()));

        Price price = priceRepository.findById(updateEvent.getPriceId())
                .orElseThrow(() -> new RuntimeException("Tarifario not found with id: " + updateEvent.getPriceId()));

        //event = eventMapper.toEntity(updateEvent);

        eventFromDB.setName(updateEvent.getName());
        eventFromDB.setDescription(updateEvent.getDescription());
        eventFromDB.setCapacity(updateEvent.getCapacity());
        eventFromDB.setImagePath(updateEvent.getImagePath());
        eventFromDB.setLocation(location);
        eventFromDB.setCategory(category);
        eventFromDB.setPrice(price);
        eventFromDB.setUpdatedAt(LocalDateTime.now());

        return  eventMapper.toDetailsDTO(eventRepository.save(eventFromDB));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non existente con id: " + id));
        eventRepository.delete(event);
    }

    @Override
    public List<EventDetailsDTO> findTop8EventsByCreatedAt() {
        return eventRepository.findTop8ByOrderByCreatedAtDesc()
                .stream()
                .map(eventMapper::toDetailsDTO)
                .toList();
    }

}
