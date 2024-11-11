package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.ResourceDTO;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResourceService {
    Resource saveResource(ResourceDTO resourceDTO, MultipartFile file) throws IOException;
    List<Resource> getAllResources();
    Resource getResourceById(Long id);

    void deleteResource(Long id);
}
