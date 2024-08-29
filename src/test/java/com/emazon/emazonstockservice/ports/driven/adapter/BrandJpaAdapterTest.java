package com.emazon.emazonstockservice.ports.driven.adapter;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandJpaAdapterTest {

    private IBrandRepository brandRepository;
    private BrandEntityMapper brandEntityMapper;
    private BrandJpaAdapter brandJpaAdapter;


    @BeforeEach
    void setup(){
        brandRepository = Mockito.mock(IBrandRepository.class);
        brandEntityMapper = Mockito.mock(BrandEntityMapper.class);
        brandJpaAdapter = new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    void saveBrand_ShouldInvokeSaveOnRepository() {

        //Arrange
        Brand brand = new Brand();
        brand.setName("brand_name");
        brand.setDescription("brand_description");

        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toEntity(any(Brand.class))).thenReturn(brandEntity);

        //act
        brandJpaAdapter.saveBrand(brand);

        //assert
        verify(brandEntityMapper).toEntity(brand);
        verify(brandRepository).save(brandEntity);
    }

    @Test
    void existsByName_ShouldReturnTrue_WhenBrandExists() {
        // Arrange
        String brandName = "brand_name";

        BrandEntity brandEntity = new BrandEntity();
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));

        // Act
        boolean result = brandJpaAdapter.existsByName(brandName);

        // Assert
        assertTrue(result);
        verify(brandRepository, times(1)).findByName(brandName);
    }

    @Test
    void existsByName_ShouldReturnFalse_WhenBrandDoesNotExist() {
        // Arrange
        String categoryName = "NonExistingBrand";

        when(brandRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // Act
        boolean result = brandJpaAdapter.existsByName(categoryName);

        // Assert
        assertFalse(result);
        verify(brandRepository, times(1)).findByName(categoryName);
    }


}