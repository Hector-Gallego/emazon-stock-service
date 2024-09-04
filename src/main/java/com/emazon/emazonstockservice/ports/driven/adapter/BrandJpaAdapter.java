package com.emazon.emazonstockservice.ports.driven.adapter;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mapper.BrandPageMapper;
import com.emazon.emazonstockservice.ports.driven.repository.IBrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    public BrandJpaAdapter(IBrandRepository brandRepository, BrandEntityMapper brandEntityMapper) {
        this.brandRepository = brandRepository;
        this.brandEntityMapper = brandEntityMapper;
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
