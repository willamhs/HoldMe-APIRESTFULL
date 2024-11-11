package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.InscriptionCreateUpdateDTO;
import com.holdme.holdmeapi_restfull.dto.InscriptionDetailsDTO;
import jakarta.mail.MessagingException;
import java.util.List;

public interface InscriptionService {
    InscriptionDetailsDTO create(InscriptionCreateUpdateDTO inscriptionDTO) throws MessagingException;
    //List<InscriptionReportDTO> getInscriptionPerEventReport();
    void delete(Integer eventId);
    List<InscriptionDetailsDTO> getAllInscription();
    List<InscriptionDetailsDTO> getInscriptionHistoryByUserId();
    InscriptionDetailsDTO confirmInscription(Integer inscriptionId);
    InscriptionDetailsDTO getInscriptionById(Integer id);

    //List<InscriptionReportDTO> getInscriptionEventReportDate();
}
