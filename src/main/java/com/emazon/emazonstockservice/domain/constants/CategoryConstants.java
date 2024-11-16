package com.emazon.emazonstockservice.domain.constants;

public final class CategoryConstants {


    private CategoryConstants(){
        throw new IllegalStateException();
    }

    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final String CATEGORY_NOT_FOUND = "La categoría con ID %d no fue encontrada en el sistema.";

    public static final String CATEGORY_CREATED_SUCCESSFULLY = "Categoría creada con éxito";
    public static final String CATEGORIES_RETRIEVED_SUCCESSFULLY = "Categorías recuperadas con éxito";
    public static final String CATEGORY_IDS_CANNOT_BE_NULL = "Los IDs de las categorías no pueden ser nulos";
    public static final String CATEGORY_IDS_CANNOT_BE_EMPTY = "Los IDs de las categorías no pueden estar vacíos";
    public static final String MAX_CATEGORY_DESCRIPTION_LENGTH_MESSAGE = "La descripción no puede exceder 90 caracteres.";
    public static final String MAX_CATEGORY_NAME_LENGTH_MESSAGE = "El nombre no puede exceder 50 caracteres.";
}
