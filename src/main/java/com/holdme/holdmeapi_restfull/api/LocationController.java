package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.LocationDTO;
import com.holdme.holdmeapi_restfull.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/location")
@RestController
@PreAuthorize("hasRole('CUSTOMER')") // Aplicar la restriccion a nivel de clase

public class LocationController {
    private final LocationService locationService;
    @GetMapping
    public ResponseEntity<List<LocationDTO>> list() {
        List<LocationDTO> locations = locationService.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<LocationDTO>> paginate(
            @PageableDefault(size = 5, sort ="location") Pageable pageable) {
        Page<LocationDTO> locations = locationService.paginate(pageable);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> search(@PathVariable("id") Integer id) {
        LocationDTO location = locationService.findById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationDTO> create (@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO newLocation = locationService.create(locationDTO);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> update(@PathVariable("id") Integer id,@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedCategoria = locationService.update(id, locationDTO);
        return new ResponseEntity<>( updatedCategoria,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
