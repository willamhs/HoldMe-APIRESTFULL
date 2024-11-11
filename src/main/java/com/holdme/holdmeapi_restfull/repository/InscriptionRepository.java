package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.Inscription;
import com.holdme.holdmeapi_restfull.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {

    Optional<Inscription> findByEventAndUser(Event event, User user);

    List<Inscription> findByUserId(Integer userId);

    //@Query(value = "SELECT * FROM fn_list_inscriptions_per_event_report()", nativeQuery = true)
    @Query(value = "SELECT quantity, consultdate FROM fn_list_inscriptions_per_event_report()", nativeQuery = true)
    List<Object[]> getInscriptionEventReportDate();

}
