package com.holdme.holdmeapi_restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {
    private String title;
    private String url;
    private String type;
    private String category;
    private String description;
}
