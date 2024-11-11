package com.holdme.holdmeapi_restfull.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventDetailsDTO {

    private Integer id;
    private String name;
    private Integer capacity;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String imagePath;
    private String categoryName;
    private String locationName;
    private String cityName;
    private Float PriceValue;
}

