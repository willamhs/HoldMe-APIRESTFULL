package com.holdme.holdmeapi_restfull.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_event_categories"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_event_locations"))
    private Location location;

    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_event_prices"))
    private Price price;
}