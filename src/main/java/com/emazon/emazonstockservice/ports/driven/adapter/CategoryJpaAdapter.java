package com.emazon.emazonstockservice.ports.driven.adapter;



import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
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

    @Override
    public CustomPage<Category> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CategoryEntity> page = categoryRepository.findAll(pageable);

        CategoryPageMapper categoryPageMapper = new CategoryPageMapper(categoryEntityMapper);

        return categoryPageMapper.toCustomPage(page);


    }


}
