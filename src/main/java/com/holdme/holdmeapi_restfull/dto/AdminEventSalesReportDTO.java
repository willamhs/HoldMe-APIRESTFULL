package com.holdme.holdmeapi_restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventSalesReportDTO {
    private String nameEvent;
    private Integer quantity;
}
