package com.emazon.emazonstockservice.domain.constants;

public final class BrandConstants {

    private BrandConstants() {
        throw new IllegalStateException();
    }
    public static final String BRAND_CREATED_SUCCESSFULLY = "Marca creada con éxito.";
    public static final String MAX_BRAND_DESCRIPTION_LENGTH_MESSAGE = "La descripción no puede exceder los 120 caracteres";
    public static final String MAX_BRAND_NAME_LENGTH_MESSAGE = "El nombre no puede exceder los 50 caracteres";

    public static final int MAX_BRAND_NAME_LENGTH = 50;
    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;
    public static final String BRAND_NOT_FOUND = "La marca con ID %d no fue encontrada en el sistema";
    public static final String BRAND_ID_CANNOT_BE_NULL = "El ID de la marca no puede ser nulo";

    public static final String  BRANDS_RETRIEVED_SUCCESSFULLY ="Marcas recuperadas con éxito";

}
