package com.emazon.emazonstockservice.ports.driven.entity;

import com.emazon.emazonstockservice.domain.util.DomainsConstants;
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


    @Column(unique = true, nullable = false, length = DomainsConstants.MAX_BRAND_NAME_LENGTH)
    private String name;
    @Column(nullable = false, length = DomainsConstants.MAX_BRAND_DESCRIPTION_LENGTH)
    private String description;
}
