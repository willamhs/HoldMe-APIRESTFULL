package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestDTO;
import com.holdme.holdmeapi_restfull.dto.CustomerEventInterestRequestDTO;
import com.holdme.holdmeapi_restfull.service.CustomerEventInterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-event-interest")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')") // Permitir solo a Student
public class CustomerEventInterestController {
    private final CustomerEventInterestService studentEventInterestService;

    public CustomerEventInterestController(CustomerEventInterestService studentEventInterestService) {
        this.studentEventInterestService = studentEventInterestService;
    }

    //Endopint para que el cliente para marque interes en un evento

    @PostMapping
    public ResponseEntity<CustomerEventInterestDTO> create(@RequestBody CustomerEventInterestRequestDTO studentEventInterestRequestDTO) {
        CustomerEventInterestDTO result = studentEventInterestService.create(studentEventInterestRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //Endpoint para obtener todos los interes de un cliente por su ID
    @GetMapping("/customer")
    public ResponseEntity<List <CustomerEventInterestDTO>>findByStudentId() {
        List<CustomerEventInterestDTO> result = studentEventInterestService.findByCustomerId();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    //Endpoint para obtener todos los intereses de un evento por us ID
    public ResponseEntity<List <CustomerEventInterestDTO>>findByEventId(@PathVariable Integer id) {
     List<CustomerEventInterestDTO> result = studentEventInterestService.findByEventId(id);
     return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteInterestByEventId(@PathVariable Integer eventId) {
        studentEventInterestService.deleteByEventId(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/is-favorite/{eventId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Integer eventId) {
        boolean isFavorite = studentEventInterestService.isEventFavorite(eventId);
        return ResponseEntity.ok(isFavorite);
    }

}
