package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.CustomerEventInterest;
import com.holdme.holdmeapi_restfull.model.entity.CustomerEventInterestPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerEventInterestRepository extends JpaRepository<CustomerEventInterest, CustomerEventInterestPK> {
    @Modifying
    @Query(value = "INSERT INTO event_participant (id_event, id_participant) " + "VALUES (:idEvent, :idParticipant)", nativeQuery = true)
    CustomerEventInterest insertEventParticipant(@Param("idEvent") Integer idEvent,
                                                 @Param("idParticipant") Integer idParticipant);

    boolean existsByCustomerIdAndEventId(Integer studentId, Integer eventId);
    void deleteByCustomerIdAndEventId(Integer studentId, Integer eventId);

}
