package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.ResourceCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.ResourceDetailsDTO;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ResourceService {

    List<ResourceDetailsDTO> findAll();
    ResourceDetailsDTO findById(Integer id);
    ResourceDetailsDTO create(ResourceCreateUpdateDTO resourceCreateUpdateDTO);
    ResourceDetailsDTO update(Integer id, ResourceCreateUpdateDTO updateResourceDTO);
    void delete(Integer id);
    List<ResourceDetailsDTO> getResourcesFiltered(String title, ResourceCategory category, ResourceType type);
}
