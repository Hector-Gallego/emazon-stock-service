package com.emazon.emazonstockservice.ports.driven.mysql.entity;


import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(unique = true, nullable = false, length = CategoryConstants.MAX_CATEGORY_NAME_LENGTH)
    private String name;
    @Column(nullable = false, length = CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH)
    private String description;



}
