package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Optional<Rating> findByRate(Integer rating);
        Optional<Rating> findByEventIdAndUserId(Integer eventId, Integer userId);

    @Query(value = "SELECT * FROM fn_list_puntuaciones_report() ", nativeQuery = true)
    List<Object[]> getRatingReportByDate();
}