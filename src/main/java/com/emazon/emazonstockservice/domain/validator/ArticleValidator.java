package com.emazon.emazonstockservice.domain.validator;

import com.emazon.emazonstockservice.domain.constants.ArticleConstants;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateCategoryException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidCategoryCountException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidValuesException;
import com.emazon.emazonstockservice.domain.model.Article;

import java.util.HashSet;
import java.util.List;

public final class ArticleValidator {

    private ArticleValidator() {
        throw new IllegalStateException();
    }

    public static void validateCategoryCount(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty() || categoryIds.size() > 3 ){
            throw new InvalidCategoryCountException(ArticleConstants.CATEGORY_COUNT_OUT_OF_RANGE);
        }

    }

    public static void validateNoDuplicateCategories(List<Long> categoryIds) {
        if (categoryIds.size() != new HashSet<>(categoryIds).size()) {
            throw new DuplicateCategoryException(ArticleConstants.ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES);
        }
    }

    public static void validatePrice(Double price){

        if(price == null || price < 0){
            throw new InvalidValuesException(String.format(
                    ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                    ArticleConstants.ARTICLE_FIELDS.PRICE));
        }

    }

    public static void validateQuantity(Integer quantity){

        if(quantity == null || quantity < 0){
            throw new InvalidValuesException(String.format(
                    ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                    ArticleConstants.ARTICLE_FIELDS.QUANTITY));
        }

    }

    public static void  validateName(String name){
        if (name == null || name.isBlank()){
            throw new FieldEmptyException(ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY);
        }
    }

    public static void  validateBrandId(Long brandId){
        if (brandId == null || brandId < 0){
            throw new InvalidValuesException(ArticleConstants.VALUE_BRAND_ID_CANNOT_BE_NEGATIVE);
        }
    }

    public static void validateDescription(String description){

        if (description == null || description.isBlank()){
            throw new FieldEmptyException(ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY);
        }


    }

    public static void validateArticleFields(Article article, List<Long> categoriesId, Long brandId){

        validateCategoryCount(categoriesId);
        validateNoDuplicateCategories(categoriesId);
        validatePrice(article.getPrice());
        validateName(article.getName());
        validateDescription(article.getDescription());
        validateQuantity(article.getQuantity());
        validateBrandId(brandId);

    }

}
