package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDetailsDTO {
    private Integer id;
    private String title;
    private String url;
    private ResourceType type;
    private ResourceCategory category;
    private String description;
    private String filePath; // Ruta del archivo en el servidor o URL
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
