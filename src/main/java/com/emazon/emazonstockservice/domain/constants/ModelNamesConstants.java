package com.emazon.emazonstockservice.domain.constants;

public enum ModelNamesConstants {
    CATEGORY("Categoría"),
    BRAND("Marca"),
    ARTICLE("Artículo");

    private final String spanishName;

    ModelNamesConstants(String spanishName) {
        this.spanishName = spanishName;
    }

    public String getSpanishName() {
        return spanishName;
    }
}

