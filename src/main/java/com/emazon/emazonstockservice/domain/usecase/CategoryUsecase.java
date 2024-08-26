package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.exceptions.CategorySaveException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;


public class CategoryUsecase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUsecase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public void saveCategory(Category category) {

        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new CategorySaveException(DomainsConstants.getDuplicateNameFieldMessage(category.getName()));
        }

        try {
            categoryPersistencePort.saveCategory(category);
        }catch (Exception exception){
            throw new CategorySaveException(DomainsConstants.FAIL_SAVE_CATEGORY_MESSAGE);
        }
    }


}
