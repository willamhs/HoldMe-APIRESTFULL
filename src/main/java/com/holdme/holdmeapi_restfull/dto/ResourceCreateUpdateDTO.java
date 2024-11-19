package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ResourceCreateUpdateDTO {
    private String title;
    private String url;
    private ResourceType type;
    private ResourceCategory category;
    private String description;
    private String filePath;
}
