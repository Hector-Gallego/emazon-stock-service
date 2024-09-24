package com.emazon.emazonstockservice.ports.driven.mysql.adapter;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.ports.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper.BrandPageMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IBrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class BrandJpaAdapter implements BrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    public BrandJpaAdapter(IBrandRepository brandRepository, BrandEntityMapper brandEntityMapper) {
        this.brandRepository = brandRepository;
        this.brandEntityMapper = brandEntityMapper;
    }


    @Override
    public Optional<Brand> findBrandById(Long brandId) {
        return brandRepository.findById(brandId).map(brandEntityMapper::toDomain);
    }

    @Override
    public void saveBrand(Brand brand) {
       brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

    @Override
    public CustomPage<Brand> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<BrandEntity> page = brandRepository.findAll(pageable);
        BrandPageMapper brandPageMapper = new BrandPageMapper(brandEntityMapper);
        
        return  brandPageMapper.toCustomPage(page);


    }
}
