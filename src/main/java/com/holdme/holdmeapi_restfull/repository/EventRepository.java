package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByNameAndDescription(String name, String description);

    @Query(value = "SELECT * FROM fn_filer_events(?, ?, ?, ?)", nativeQuery = true)
    List<Object[]> getEventsFiltered(Float precioMin, Float precioMax, String categoriaName, String ubicacion);
  
    List<Event> findTop8ByOrderByCreatedAtDesc();
}
