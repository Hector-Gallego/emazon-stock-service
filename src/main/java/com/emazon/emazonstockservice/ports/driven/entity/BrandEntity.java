package com.emazon.emazonstockservice.ports.driven.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "brand")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 120)
    private String description;
}
