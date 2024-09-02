package com.emazon.emazonstockservice.domain.util;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateCategoryException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidCategoryCountException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidValuesException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ArticleValidator {

    private ArticleValidator() {
        throw new IllegalStateException();
    }

    public static void validateCategoryCount(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty() || categoryIds.size() > 3 ){
            throw new InvalidCategoryCountException(ArticleConstans.CATEGORY_COUNT_OUT_OF_RANGE);
        }

    }

    public static void validateNoDuplicateCategories(List<Long> categoryIds) {
        if (categoryIds.size() != new HashSet<>(categoryIds).size()) {
            throw new DuplicateCategoryException(ArticleConstans.ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES);
        }
    }

    public static void validatePrice(Double price){

        if(price == null || price < 0){
            throw new InvalidValuesException(String.format(
                    ArticleConstans.VALUE_CANNOT_BE_NEGATIVE,
                    ArticleConstans.ARTICLE_FIELDS.PRICE));
        }

    }

    public static void validateQuantity(Integer quantity){

        if(quantity == null || quantity < 0){
            throw new InvalidValuesException(String.format(
                    ArticleConstans.VALUE_CANNOT_BE_NEGATIVE,
                    ArticleConstans.ARTICLE_FIELDS.QUANTITY));
        }

    }

    public static void  validateName(String name){
        if (name == null || name.isBlank()){
            throw new FieldEmptyException(DomainsConstants.NAME_CANNOT_BE_EMPTY);
        }
    }

    public static void validateDescription(String description){

        if (description == null || description.isBlank()){
            throw new FieldEmptyException(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY);
        }


    }

}
