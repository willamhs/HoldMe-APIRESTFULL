package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.RatingCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.RatingDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.PuntuacionReportDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface RatingService {
    List<PuntuacionReportDTO> getRateReportByDate();

    List<RatingDetailsDTO> findAll();

    RatingDetailsDTO findById(Integer id);

    RatingDetailsDTO create(RatingCreateUpdateDTO ratingCreateDTO) throws MessagingException;

    RatingDetailsDTO update(Integer id, RatingCreateUpdateDTO ratingUpdateDTO);

    void delete(Integer id);
}