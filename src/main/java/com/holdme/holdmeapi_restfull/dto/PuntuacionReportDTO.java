package com.holdme.holdmeapi_restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntuacionReportDTO {
    private Integer quantity;
    private String consultDate;
}
