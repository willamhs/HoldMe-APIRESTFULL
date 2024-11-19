package com.holdme.holdmeapi_restfull.repository;

import com.holdme.holdmeapi_restfull.model.entity.Resource;
import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Optional<Resource> findByTitle(String title);
    List<Resource> findByDescription(String description);

    // Consulta personalizada para filtrado por titulo, categoria y tipo con Enum
    @Query("SELECT r FROM Resource r WHERE " +
            "(:titulo IS NULL OR r.title LIKE %:titulo%) " +
            "AND (:categoria IS NULL OR r.category = :categoria) " +
            "AND (:tipo IS NULL OR r.type = :tipo)")
    List<Resource> getResourcesFiltered(@Param("titulo") String title,
                                        @Param("categoria") ResourceCategory category,
                                        @Param("tipo") ResourceType type);
}
