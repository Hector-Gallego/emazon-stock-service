package com.emazon.emazonstockservice.domain.constants;

public final class ErrorMessagesConstants {



    private ErrorMessagesConstants(){
        throw new IllegalStateException();
    }



    public static final String MAX_NAME_LENGTH_ERROR_MESSAGE = "El campo Nombre no puede exceder %d caracteres.";
    public static final String MAX_DESCRIPTION_LENGTH_ERROR_MESSAGE = "La Descripción no puede exceder %d caracteres.";

    public static final String NAME_CANNOT_BE_NULL_ERROR_MESSAGE = "El Nombre no puede ser nulo.";
    public static final String DESCRIPTION_CANNOT_BE_NULL_ERROR_MESSAGE = "La Descripción no puede ser nula.";
    public static final String NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE = "El Nombre no puede estar vacío.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE = "La Descripción no puede estar vacía.";

    public static final String INVALID_PAGE_NO_ERROR_MESSAGE = "Número de página inválido. Debe ser un entero no negativo.";
    public static final String INVALID_PAGE_SIZE_ERROR_MESSAGE = "Tamaño de página inválido. Debe ser un entero positivo.";
    public static final String INVALID_SORT_DIRECTION_ERROR_MESSAGE = "Dirección de ordenamiento inválida. Debe ser 'asc' o 'desc'.";
    public static final String INVALID_SORT_BY_ERROR_MESSAGE = "Campo de ordenamiento inválido: ";
    public static final String INVALID_PARAMETERS_ERROR_MESSAGE = "Uno o más parámetros son inválidos.";

    public static final String ARTICLE_NOT_FOUND = "No se encontró el artículo con id: %d";

    public static final String BAD_REQUEST_ERROR_MESSAGE = "La solicitud no se pudo procesar debido a entradas inválidas.";
    public static final String GENERIC_ERROR_MESSAGE = "Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE= "Ocurrió un error interno en el servidor. Por favor, contacte al soporte.";
    public static final String NOT_FOUND_ERROR_MESSAGE = "El recurso solicitado no fue encontrado.";
    public static final String UNAUTHORIZED_ERROR_MESSAGE = "No está autorizado para acceder a este recurso.";
    public static final String ACCESS_DENIED_ERROR_MESSAGE = "Acceso denegado al recurso solicitado.";

    public static final String QUANTITY_CANNOT_BE_NULL_ERROR_MESSAGE = "La cantidad no puede ser nula.";
    public static final String QUANTITY_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE = "La cantidad debe ser cero o un número positivo.";
    public static final String PRICE_CANNOT_BE_NULL_ERROR_MESSAGE = "El precio no puede ser nulo.";
    public static final String PRICE_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE = "El precio debe ser cero o un número positivo.";
    public static final String INVALID_FIELDS = "Uno o más campos son inválidos.";
    public static final String ARTICLE_NOT_FOUND_ERROR_MESSAGE = "No se encontró el artículo %d.";

    public static final String INSUFFICIENT_STOCK_ERROR_MESSAGE = "No hay suficiente stock para el artículo con ID %d. Disponible: %d, Requerido: %d";



    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("La %s con el nombre: '%s' ya existe.", field, name);
    }

    public static String getDuplicateNameFieldArticleMessage( String field, String name) {
        return String.format("El %s con el nombre: '%s' ya existe.", field, name);
    }


}
