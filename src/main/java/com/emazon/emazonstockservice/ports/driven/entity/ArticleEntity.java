package com.emazon.emazonstockservice.ports.driven.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "article")
public class ArticleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleCategoryEntity> articleCategoryEntities;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

}
