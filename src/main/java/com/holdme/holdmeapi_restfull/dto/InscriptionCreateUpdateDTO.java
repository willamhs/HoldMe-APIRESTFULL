package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.InscriptionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InscriptionCreateUpdateDTO {
    private Integer id;
    private InscriptionStatus inscriptionStatus;
    private LocalDateTime inscriptionDate;

    //@NotNull(message = "El evento es obligatorio")
    //private Integer eventId;

    @NotNull(message = "El usuario es obligatorio")
    private Integer customerId;

    private Float total;
    private List<InscriptionItemCreateUpdateDTO> items;

}
