package com.holdme.holdmeapi_restfull.mapper;
import com.holdme.holdmeapi_restfull.dto.ResourceCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.ResourceDetailsDTO;
import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class ResourceMapper {
    private final ModelMapper modelMapper;

    public ResourceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // Mapea la entidad Resource a ResourceDetailsDTO (detalles de un recurso)
    public ResourceDetailsDTO toDetailsDTO(Resource resource) {
        // Mapea la entidad Resource a ResourceDetailsDTO de manera automática
        ResourceDetailsDTO resourceDetailsDTO = modelMapper.map(resource, ResourceDetailsDTO.class);

        // Aquí puedes personalizar la información adicional que desees agregar al DTO

        // Si deseas representar el tipo como un texto descriptivo o hacer alguna manipulación adicional
        resourceDetailsDTO.setType(resource.getType()); // "VIDEO", "BOOK", "IMAGE", etc.
        resourceDetailsDTO.setCategory(resource.getCategory()); // "EDUCATIONAL", "MOTIVATIONAL", etc.

        // Si el tipo de recurso tiene lógica asociada, como por ejemplo la URL del archivo o un mensaje específico
        resourceDetailsDTO.setDescription(resource.getDescription());

        // También se puede agregar información del campo 'createdAt' y 'updatedAt' si es necesario
        resourceDetailsDTO.setCreatedAt(resource.getCreatedAt());
        resourceDetailsDTO.setUpdatedAt(resource.getUpdatedAt());

        // Si deseas incluir el archivo, puedes usar el filePath para mostrar la ruta o la URL
        resourceDetailsDTO.setFilePath(resource.getFilePath());

        return resourceDetailsDTO;
    }

    // Mapea ResourceCreateUpdateDTO a la entidad Resource
    public Resource toEntity(ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        return modelMapper.map(resourceCreateUpdateDTO, Resource.class);
    }

    // Mapea la entidad Resource a ResourceCreateUpdateDTO
    public ResourceCreateUpdateDTO toCreateUpdateDTO(Resource resource) {
        return modelMapper.map(resource, ResourceCreateUpdateDTO.class);
    }
}
