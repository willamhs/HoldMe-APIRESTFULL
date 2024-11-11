package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.InscriptionCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.InscriptionDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.InscriptionItemCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.InscriptionItemDTO;
import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.Inscription;
import com.holdme.holdmeapi_restfull.model.entity.InscriptionItem;
import com.holdme.holdmeapi_restfull.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Component
public class InscriptionMapper {

    private final ModelMapper modelMapper;

    public InscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    //Convertir PurchaseCreateUpdateDTO a Purchase (Crear una event)
    public Inscription toInscriptionEntity(InscriptionCreateUpdateDTO inscriptionCreateUpdateDTO) {
        Inscription inscription = modelMapper.map(inscriptionCreateUpdateDTO, Inscription.class);


        User user = new User();
        user.setId(inscriptionCreateUpdateDTO.getCustomerId());
        inscription.setUser(user);

        //Mapear manualmente los items del evento
        inscription.setItems(inscriptionCreateUpdateDTO.getItems().stream()
                .map(this::toInscriptionItemEntity)
                .toList());

        return inscription;
    }

    //Convertir Purchase a PurchaseDTO (Mostrar una compra)
    public InscriptionDetailsDTO toInscriptionDTO(Inscription inscription) {
        InscriptionDetailsDTO inscriptionDetailsDTO = modelMapper.map(inscription, InscriptionDetailsDTO.class);

        inscriptionDetailsDTO.setItems(inscription.getItems().stream()
                .map(this::toInscriptionItemDTO)
                .toList());
        inscriptionDetailsDTO.setCustomerName(inscription.getUser().getCustomer().getFirstName() + " " + inscription.getUser().getCustomer().getLastName());
        inscriptionDetailsDTO.setCreatedAt(LocalDateTime.now());
        return inscriptionDetailsDTO;
    }

    private InscriptionItem toInscriptionItemEntity(InscriptionItemCreateUpdateDTO itemDTO) {
        InscriptionItem item = modelMapper.map(itemDTO, InscriptionItem.class);
        Event event = new Event();
        event.setId(itemDTO.getEventId());
        item.setEvent(event);
        return item;
    }

    private InscriptionItemDTO toInscriptionItemDTO(InscriptionItem item) {
        InscriptionItemDTO itemDTO = modelMapper.map(item, InscriptionItemDTO.class);
        itemDTO.setNameEvent(item.getEvent().getName());
        return itemDTO;
    }
}
