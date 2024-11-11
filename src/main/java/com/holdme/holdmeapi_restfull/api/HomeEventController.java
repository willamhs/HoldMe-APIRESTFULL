package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.EventDetailsDTO;
import com.holdme.holdmeapi_restfull.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class HomeEventController {

    private final EventService eventService;

    //Endpoint para obtener los 8 eventos mas recientes
    @GetMapping("/recent")
    public ResponseEntity<List<EventDetailsDTO>> getRecentEvents() {
        List<EventDetailsDTO> recentEvents = eventService.findTop8EventsByCreatedAt();
        return new ResponseEntity<>(recentEvents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailsDTO> search(@PathVariable("id") Integer id){
        EventDetailsDTO event = eventService.findById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
