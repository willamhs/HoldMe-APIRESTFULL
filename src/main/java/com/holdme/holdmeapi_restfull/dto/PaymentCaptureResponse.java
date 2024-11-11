package com.holdme.holdmeapi_restfull.dto;

import lombok.Data;

@Data
public class PaymentCaptureResponse {
    private boolean completed;
    private Integer inscriptionId;
}
