package com.emazon.emazonstockservice.domain.constants;

public final class ArticleConstants {

    private ArticleConstants() {
        throw new IllegalStateException();
    }

    public enum ARTICLE_FIELDS {
        NAME,
        DESCRIPTION,
        QUANTITY,
        PRICE
    }
    public static final String ARTICLES_RETRIEVED_SUCCESSFULLY = "Artículos recuperados con éxito";

    public static final String ARTICLE_CREATED_SUCCESSFULLY = "Artículo creado de manera exitosa";

    public static final String ARTICLE_NOT_FOUND = "EL artículo con ID %d no fue encontrado en el sistema.";
    public static final String VALUE_CANNOT_BE_NEGATIVE = "El precio del %s no puede ser menor que 0.";
    public static final String CATEGORY_COUNT_OUT_OF_RANGE = "El número de categorías asociadas al artículo no está dentro del rango permitido (mínimo: 1, máximo: 3).";
    public static final String ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES = "El artículo no puede tener categorías duplicadas.";
    public static final String VALUE_BRAND_ID_CANNOT_BE_NEGATIVE = "El ID de la marca debe ser un valor no negativo.";
    public static final String STOCK_ADDED_SUCCESS = "Stock añadido con éxito";}
