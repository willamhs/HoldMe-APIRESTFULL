package com.holdme.holdmeapi_restfull.dto;

import lombok.Data;

@Data
public class RatingDetailsDTO {

    private Integer id;

    private Integer rate;

    private String eventName;

    private String customerName;

}