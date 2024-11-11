package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Event;
import com.holdme.holdmeapi_restfull.model.entity.EventProgramming;
import com.holdme.holdmeapi_restfull.model.entity.EventProgrammingPK;
import com.holdme.holdmeapi_restfull.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventProgrammingRepository extends JpaRepository<EventProgramming, EventProgrammingPK> {
    List<EventProgramming> findByEvent(Event event);
    List<EventProgramming> findBySchedule(Schedule schedule);
    @Query(value = "SELECT * FROM get_user_event_programming(:studentId)", nativeQuery = true)
    List<Object[]> getUserEventProgramming(@Param("studentId")Integer studentId);

}
