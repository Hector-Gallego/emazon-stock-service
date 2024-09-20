package com.emazon.emazonstockservice.ports.driven.mysql.sortutils;

public enum ArticleSortOptions implements ISortOptions {

    CATEGORY_NAME("categoryEntities.name"),
    BRAND_NAME("brandEntity.name"),
    NAME("name");

    private final String field;

    ArticleSortOptions(String field) {
        this.field = field;
    }
    public String getField() {
        return field;
    }

}
