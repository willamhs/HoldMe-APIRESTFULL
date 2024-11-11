package com.holdme.holdmeapi_restfull.api;

import com.holdme.holdmeapi_restfull.dto.UserEventProgrammingDTO;
import com.holdme.holdmeapi_restfull.service.EventProgrammingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class calendarController {
    private final EventProgrammingService eventProgrammingService;

    @GetMapping("/user")
    public ResponseEntity<List<UserEventProgrammingDTO>> getUserEventProgramming() {
        List<UserEventProgrammingDTO> eventProgramming = eventProgrammingService.getUserEventProgramming();
        return ResponseEntity.ok(eventProgramming);
    }
}
