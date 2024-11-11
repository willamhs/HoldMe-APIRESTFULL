package com.holdme.holdmeapi_restfull.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "price", nullable = false,columnDefinition = "DECIMAL")
    private Float price;
    @Column(name = "description",nullable = false,columnDefinition = "TEXT")
    private String description;
}