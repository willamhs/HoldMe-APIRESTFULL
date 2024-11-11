package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.PriceDTO;
import com.holdme.holdmeapi_restfull.model.entity.Price;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    private final ModelMapper modelMapper;

    public PriceMapper(ModelMapper modelMapper) {
        this.modelMapper =  modelMapper;
    }

    public PriceDTO toDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }

    public Price toEntity(PriceDTO priceDTO) {
        return modelMapper.map(priceDTO, Price.class);
    }
}
