package com.holdme.holdmeapi_restfull.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerEventInterestDTO {
    private Integer customerId;
    private Integer eventId;
    private String eventName;
    private LocalDateTime eventCreatedAt;
    private String eventLocationName;
    private BigDecimal eventPriceAmount;
}
