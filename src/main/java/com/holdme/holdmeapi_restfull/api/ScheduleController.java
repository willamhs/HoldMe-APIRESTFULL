package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.ScheduleDTO;
import com.holdme.holdmeapi_restfull.model.entity.Schedule;
import com.holdme.holdmeapi_restfull.service.ScheduleService;
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
@RequestMapping("/admin/schedule")
@RestController
@PreAuthorize("hasRole('ADMIN')") // Aplicar la restriccion a nivel de clase

public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findAll() {
        List<ScheduleDTO> schedules = scheduleService.findAll();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ScheduleDTO>> paginate(
            @PageableDefault(size = 5, sort = "start_hour")Pageable pageable) {
        Page<ScheduleDTO> schedules = scheduleService.paginate(pageable);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable("id") Integer id) {
        ScheduleDTO schedule = scheduleService.findById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> create(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO newSchedule = scheduleService.create(scheduleDTO);
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> update(@PathVariable("id") Integer id,
                                           @Valid@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO updateSchedule = scheduleService.update(id, scheduleDTO);
        return new ResponseEntity<>(updateSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> delete(@PathVariable("id") Integer id) {
        scheduleService.delete(id);
        return new ResponseEntity<Schedule>(HttpStatus.NO_CONTENT);
    }
}
