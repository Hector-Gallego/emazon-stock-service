package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import com.emazon.emazonstockservice.domain.util.FieldValidator;
import com.emazon.emazonstockservice.domain.util.PaginationValidator;



public class CategoryUseCase implements ICategoryServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public void saveCategory(Category category) {

        FieldValidator.validateNameAndDescription(
                category.getName(),
                MAX_NAME_LENGTH,
                category.getDescription(),
                MAX_DESCRIPTION_LENGTH
        );

        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new DuplicateNameException(DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.MODEL_NAMES.CATEGORY.toString(),category.getName()));
        }

        categoryPersistencePort.saveCategory(category);

    }

    @Override
    public CustomPage<Category> listCategories(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        Integer validatedPageNo = PaginationValidator.validatePageNo(pageNo);
        Integer validatedPageSize = PaginationValidator.validatePageSize(pageSize);
        String validatedSortBy = PaginationValidator.validateSortBy(sortBy);
        String validatedSortDirection = PaginationValidator.validateSortDirection(sortDirection);

        return  categoryPersistencePort.findAll(validatedPageNo, validatedPageSize, validatedSortBy, validatedSortDirection);
    }


}
