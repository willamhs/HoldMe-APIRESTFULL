package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.InscriptionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InscriptionDetailsDTO {

    private Integer id;
    private InscriptionStatus inscriptionStatus;
    //private String nameEvent;
    private String customerName;
    private Float total;
    private LocalDateTime createdAt;
    private List<InscriptionItemDTO> items;

}
