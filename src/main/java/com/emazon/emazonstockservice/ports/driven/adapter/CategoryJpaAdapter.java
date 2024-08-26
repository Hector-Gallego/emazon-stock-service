package com.emazon.emazonstockservice.ports.driven.adapter;



import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.ports.driven.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {


    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }


}
