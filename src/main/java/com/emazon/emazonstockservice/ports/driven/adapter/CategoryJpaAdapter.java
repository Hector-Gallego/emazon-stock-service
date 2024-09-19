package com.emazon.emazonstockservice.ports.driven.adapter;


import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mapper.CategoryPageMapper;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class CategoryJpaAdapter implements CategoryPersistencePort {


    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;


    @Override
    public Map<Long, Optional<Category>> findCategoryByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> categoryRepository.findById(id)
                                .map(categoryEntityMapper::toDomain)
                ));
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public CustomPage<Category> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CategoryEntity> page = categoryRepository.findAll(pageable);

        CategoryPageMapper categoryPageMapper = new CategoryPageMapper(categoryEntityMapper);

        return categoryPageMapper.toCustomPage(page);


    }


}
