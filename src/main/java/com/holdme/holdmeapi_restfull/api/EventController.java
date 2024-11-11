package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.EventCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.EventDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.EventFilterDTO;
import com.holdme.holdmeapi_restfull.dto.FilteredEventsDTO;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')") // Aplicar la restriccion a nivel de clase
//@PreAuthorize("hasAnyRole('ADMIN','WORKER')") // Permitir a admin y worker si es que hubiera uno

public class EventController {

    private final EventService eventService;

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<EventDetailsDTO>> list(){
        List<EventDetailsDTO> events = eventService.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PostMapping("/filtered")
    public ResponseEntity<List<FilteredEventsDTO>> getFilteredEvents(@RequestBody EventFilterDTO filterDTO) {
        // Llamar al servicio pasando los valores desde el DTO
        List<FilteredEventsDTO> filteredEvents = eventService.getEventsFiltered(
                filterDTO.getPrecioMin(),
                filterDTO.getPrecioMax(),
                filterDTO.getCategoriaName(),
                filterDTO.getUbicacion()
        );
        return new ResponseEntity<>(filteredEvents, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EventDetailsDTO> search(@PathVariable("id") Integer id){
        EventDetailsDTO event = eventService.findById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EventDetailsDTO> create(@Valid @RequestBody EventCreateUpdateDTO event){
        EventDetailsDTO newEvent = eventService.create(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EventDetailsDTO> update(@PathVariable("id") Integer id,
                                                @Valid @RequestBody EventCreateUpdateDTO event){
        EventDetailsDTO updateEvent = eventService.update(id, event);
        return new ResponseEntity<>(updateEvent, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable("id") Integer id){
        eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

