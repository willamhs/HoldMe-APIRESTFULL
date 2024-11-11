package com.holdme.holdmeapi_restfull.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventProgrammingCreateUpdateDTO {
    private Integer eventId;
    private Integer scheduleId;

    @JsonFormat(pattern = "dd/MM/yy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yy")
    private LocalDate endDate;
}
