package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.CategoryServicePort;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.validator.FieldValidator;
import com.emazon.emazonstockservice.domain.validator.PaginationValidator;



public class CategoryUseCase implements CategoryServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(CategoryPersistencePort categoryPersistencePort) {
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
            throw new DuplicateNameException(ErrorMessagesConstants.getDuplicateNameFieldMessage(ModelNamesConstants.CATEGORY.toString(),category.getName()));
        }

        categoryPersistencePort.saveCategory(category);

    }

    @Override
    public CustomPage<Category> listCategories(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        PaginationValidator.validatePaginationParameters(pageNo, pageSize,sortDirection, sortBy);
        return  categoryPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection);
    }


}
