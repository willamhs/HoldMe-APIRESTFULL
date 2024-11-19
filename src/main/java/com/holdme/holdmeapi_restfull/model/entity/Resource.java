package com.holdme.holdmeapi_restfull.model.entity;

import com.holdme.holdmeapi_restfull.model.enums.ResourceCategory;
import com.holdme.holdmeapi_restfull.model.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 150)
    private String title;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceCategory category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String filePath; // Ruta del archivo en el servidor o URL

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Fecha de creación

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Fecha de última actualización
}
