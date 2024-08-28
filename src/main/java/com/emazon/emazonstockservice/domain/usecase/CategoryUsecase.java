package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.exceptions.CategorySaveException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import com.emazon.emazonstockservice.domain.util.FieldValidator;
import com.emazon.emazonstockservice.domain.util.PaginationValidator;


public class CategoryUsecase implements ICategoryServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUsecase(ICategoryPersistencePort categoryPersistencePort) {
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
            throw new CategorySaveException(DomainsConstants.getDuplicateNameFieldMessage(category.getName()));
        }

        try {
            categoryPersistencePort.saveCategory(category);
        }catch (Exception exception){
            throw new CategorySaveException(DomainsConstants.FAIL_SAVE_CATEGORY_MESSAGE);
        }
    }

    @Override
    public CustomPage<Category> listCategories(int pageNo, int pageSize, String sortBy, String sortDirection) {

        int validatedPageNo = PaginationValidator.validatePageNo(pageNo);
        int validatedPageSize = PaginationValidator.validatePageSize(pageSize);
        String validatedSortBy = PaginationValidator.validateSortBy(sortBy);
        String validatedSortDirection = PaginationValidator.validateSortDirection(sortDirection);

        return  categoryPersistencePort.findAll(validatedPageNo, validatedPageSize, validatedSortBy, validatedSortDirection);
    }


}
